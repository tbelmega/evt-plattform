// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package de.belmega.eventers.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import de.belmega.eventers.booking.BookingBean;
import de.belmega.eventers.booking.BookingDAO;
import de.belmega.eventers.booking.BookingEntity;
import de.belmega.eventers.booking.PaymentStatus;
import de.belmega.eventers.services.categories.ServiceDAO;
import de.belmega.eventers.services.categories.ServiceEntity;
import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PaypalPaymentServlet extends HttpServlet {

    public static final String PAYER_ID = "payerID";
    public static final String PAYMENT_ID = "paymentID";
    public static final String BOOKING_ID = "bookingId";
    public static final String SERVICE_ID = "serviceId";

    // The currency for paypal
    public static final String EURO = "EUR";

    @Inject
    private ServiceDAO serviceDAO;




    @Inject
    private PaypalService paypalService;

    private static final Logger LOGGER = Logger.getLogger(PaypalPaymentServlet.class);




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        if (isExistingPayment(req)) {
            paypalService.executePayment(req.getParameter(PAYMENT_ID), req.getParameter(PAYER_ID), req.getParameter(BOOKING_ID));

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            Payment createdPayment = createNewPayment(req);
            JSONObject paymentRepresentation = new JSONObject();
            paymentRepresentation.put(PAYMENT_ID, createdPayment.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(paymentRepresentation.toString());
        }
    }


    private Payment createNewPayment(HttpServletRequest req) throws ServletException {
        Payment createdPayment;

        // ###Details
        // Let's you specify details of a payment amount.
//        Details details = new Details();
//        details.setShipping("1");
//        details.setSubtotal("5");
//        details.setTax("1");

        String serviceId = req.getParameter(SERVICE_ID);

        // ###Amount
        // Let's you specify a payment amount.
        ServiceEntity service = serviceDAO.findServiceById(serviceId);
        String serviceName = service.getServiceName();
        int hourlyRate = service.getHourlyRate();
        String hourlyString = Integer.toString(hourlyRate);

        Amount amount = new Amount();
        amount.setCurrency(EURO);
        // Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal(hourlyString);
//        amount.setDetails(details);

        // ###Transaction
        // A transaction defines the contract of a payment - what is the payment for and who is fulfilling it.
        // Transaction is created with a `Payee` and `Amount` types
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("the-eventers.de Buchung für " + serviceName);

        // ### Items
        Item item = new Item();
        item.setName(serviceName).setQuantity("1").setCurrency(EURO).setPrice(hourlyString);
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        items.add(item);
        itemList.setItems(items);

        transaction.setItemList(itemList);


        // The Payment creation API requires a list of
        // Transaction; add the created `Transaction`
        // to a List
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // ###Payer
        // A resource representing a Payer that funds a payment Payment Method as 'paypal'
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // ###Payment
        // A Payment Resource; create one using the above types and intent as 'sale'
        Payment payment = new Payment();
        payment.setIntent("authorize");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // ###Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl(req.getScheme() + "://"
                + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
        redirectUrls.setReturnUrl(req.getScheme() + "://"
                + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);


        // Create a payment by posting to the APIService using a valid AccessToken
        // The return object contains the status

        try {

            createdPayment = payment.create(paypalService.createApiContext());
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }

        LOGGER.info("Created payment with id = "
                + createdPayment.getId() + " and status = "
                + createdPayment.getState());

        // ###Payment Approval Url
//				Iterator<Links> links = createdPayment.getLinks().iterator();
//				while (links.hasNext()) {
//					Links link = links.next();
//					if (link.getRel().equalsIgnoreCase("approval_url")) {
//						req.setAttribute("redirectURL", link.getHref());
//					}
//				}

        return createdPayment;
    }



    /**
     * Check if the request is for an existing payment or should create a new payment.
     */
    private boolean isExistingPayment(HttpServletRequest req) {
        return req.getParameter(PAYER_ID) != null;
    }



}
