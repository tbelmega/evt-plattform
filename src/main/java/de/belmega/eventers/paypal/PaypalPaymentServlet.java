// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package de.belmega.eventers.paypal;

import com.google.gson.Gson;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.log4j.Logger;
import org.primefaces.json.JSONObject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class PaypalPaymentServlet extends HttpServlet {

    private static final Logger LOGGER = Logger
            .getLogger(PaypalPaymentServlet.class);
    @Inject
    @ConfigurationValue("paypal.clientId")
    protected String clientID;
    @Inject
    @ConfigurationValue("paypal.clientSecret")
    protected String clientSecret;
    @Inject
    @ConfigurationValue("paypal.mode")
    protected String mode;

    // ### Api Context
    // Pass in a `ApiContext` object to authenticate the call and to send a unique request id
    // (that ensures idempotency). The SDK generates  a request id if you do not pass one explicitly.
    APIContext apiContext;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

         apiContext = new APIContext(clientID, clientSecret, mode);

        if (isExistingPayment(req)) {
            executePayment(req, resp, apiContext);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            Payment createdPayment = createNewPayment(req, resp, apiContext);
            JSONObject paymentRepresentation = new JSONObject();
            paymentRepresentation.put("paymentID", createdPayment.getId());

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(paymentRepresentation.toString());
        }
    }

    private Payment createNewPayment(HttpServletRequest req, HttpServletResponse resp, APIContext apiContext) throws ServletException {
        Payment createdPayment;// ###Details
        // Let's you specify details of a payment amount.
        Details details = new Details();
        details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");

        // ###Amount
        // Let's you specify a payment amount.
        Amount amount = new Amount();
        amount.setCurrency("USD");
        // Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal("7");
        amount.setDetails(details);

        // ###Transaction
        // A transaction defines the contract of a payment - what is the payment for and who is fulfilling it.
        // Transaction is created with a `Payee` and `Amount` types
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction
                .setDescription("This is the payment transaction description.");

        // ### Items
        Item item = new Item();
        item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        items.add(item);
        itemList.setItems(items);

        transaction.setItemList(itemList);


        // The Payment creation API requires a list of
        // Transaction; add the created `Transaction`
        // to a List
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        // ###Payer
        // A resource representing a Payer that funds a payment Payment Method as 'paypal'
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // ###Payment
        // A Payment Resource; create one using the above types and intent as 'sale'
        Payment payment = new Payment();
        payment.setIntent("sale");
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
            createdPayment = payment.create(apiContext);
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

        ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
        return createdPayment;
    }

    private void executePayment(HttpServletRequest req, HttpServletResponse resp, APIContext apiContext) {
        Payment payment = new Payment();
        if (req.getParameter("paymentID") != null) {
            payment.setId(req.getParameter("paymentID"));
        }

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("payerID"));
        try {
            payment.execute(apiContext, paymentExecution);
            ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
        } catch (PayPalRESTException e) {
            ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), null, e.getMessage());
        }
    }

    private boolean isExistingPayment(HttpServletRequest req) {
        return req.getParameter("payerID") != null;
    }
}
