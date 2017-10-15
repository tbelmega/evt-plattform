package de.belmega.eventers.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import de.belmega.eventers.booking.BookingDAO;
import de.belmega.eventers.booking.BookingEntity;
import de.belmega.eventers.booking.PaymentStatus;
import org.apache.log4j.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.inject.Inject;
import javax.servlet.ServletException;

public class PaypalService {

    @Inject
    private BookingDAO bookingDAO;

    @Inject
    private PaymentDAO paymentDAO;

    @Inject
    @ConfigurationValue("paypal.clientId")
    protected String clientID;

    @Inject
    @ConfigurationValue("paypal.clientSecret")
    protected String clientSecret;

    @Inject
    @ConfigurationValue("paypal.mode")
    protected String mode;

    private static final Logger LOGGER = Logger.getLogger(PaypalService.class);


    void executePayment(String paymentId, String payerId, String bookingId) throws ServletException {
        Payment payment = new Payment();
        payment.setIntent(PaymentIntent.AUTHORIZE.getId());
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        try {
            Payment paymentFromServer = payment.execute(createApiContext(), paymentExecution);
            String authorizationId = getAuthorizationId(paymentFromServer);

            updateBooking(bookingId, payment, authorizationId);
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Find the authorizationId value in the data sent from paypal server.
     */
    private String getAuthorizationId(Payment paymentFromServer) {
        return paymentFromServer.getTransactions().get(0).getRelatedResources().get(0).getAuthorization().getId();
    }

    public void captureAuthorizedPayment(Long bookingId) throws PayPalRESTException {
        BookingEntity booking = bookingDAO.findById(bookingId);
        PaymentEntity payment = booking.getPayment();

        Authorization authorization = Authorization.get(createApiContext(), payment.getAuthorizationId());

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal("1");

        // ###Capture
        // A capture transaction
        Capture capture = new Capture();
        capture.setAmount(amount);

        // Capture by POSTing to
        // URI v1/payments/authorization/{authorization_id}/capture
        Capture responseCapture = authorization.capture(createApiContext(), capture);

        LOGGER.info("Capture id = " + responseCapture.getId()
                + " and status = " + responseCapture.getState());
    }

    /**
     * Store the payment and autorization data in the database.
     */
    public void updateBooking(String bookingId, Payment payment, String authorizationId) {
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);
        paymentEntity.setPaypalPaymentId(payment.getId());
        paymentEntity.setAuthorizationId(authorizationId);

        BookingEntity booking = bookingDAO.findById(Long.parseLong(bookingId));
        paymentEntity.setBooking(booking);

        paymentDAO.persist(paymentEntity);

        booking.setPayment(paymentEntity);
    }

    // ### Api Context
    // Pass in a `ApiContext` object to authenticate the call and to send a unique request id
    // (that ensures idempotency). The SDK generates  a request id if you do not pass one explicitly.
    APIContext createApiContext() {
        return new APIContext(clientID, clientSecret, mode);
    }

}
