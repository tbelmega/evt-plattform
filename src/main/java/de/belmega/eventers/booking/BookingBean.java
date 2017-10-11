package de.belmega.eventers.booking;

import com.paypal.api.payments.Payment;
import de.belmega.eventers.mail.EmailSessionBean;
import de.belmega.eventers.paypal.PaypalPaymentServlet;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.services.categories.ServiceDAO;
import de.belmega.eventers.services.categories.ServiceEntity;
import de.belmega.eventers.user.Greeting;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserDAO;
import de.belmega.eventers.user.registration.RegisterProviderBean;
import io.undertow.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class BookingBean implements Serializable {

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private BookingDAO bookingDAO;

    @Inject
    BookingLinkDAO bookingLinkDAO;

    @Inject
    private CustomerDAO customerDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    EmailSessionBean emailSessionBean;

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    @Inject
    @ConfigurationValue("paypal.mode")
    String environment;

    @Inject
    @ConfigurationValue("paypal.servlet.payment")
    private String paymentServletUrl;


    private static final Logger LOGGER = Logger.getLogger(BookingBean.class);


    private String serviceId;
    private BookingTO booking;
    private CustomerTO customer;
    private ServiceEntity service;
    public static final SimpleDateFormat DATE_ONLY = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat TIME_ONLY = new SimpleDateFormat("HH:mm");


    public String getTitle() {
        return getService().getServiceName();
    }

    public void setServiceId(String serviceId) {
        if (StringUtils.isNotEmpty(serviceId))
            this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String submit() {
        booking = bookingDAO.persist(booking);

        List<ScheduleEventEntity> availabilities = bookingDAO.findMatchingAvailabilities(booking);

        for (ScheduleEventEntity availability : availabilities)
            sendBookingEmailToProviders(availability);

        return "booking4.xhtml?step=-1&faces-redirect=true";
    }

    private void sendBookingEmailToProviders(ScheduleEventEntity availability) {
        InputStream mailText = RegisterProviderBean.class.getClassLoader().getResourceAsStream("emails/booking.txt");
        String mailTextString = FileUtils.readFile(mailText);

        ProviderUserEntity provider = availability.getUser();

        Map<String, String> data = new HashMap<>();
        data.put("greeting-provider", provider.getGreeting().getGreeting() + " " + provider.getLastname());
        data.put("greeting-customer", customer.getGreeting().getName() + " "
                + customer.getFirstname() + " " + customer.getLastname() + " " + customer.getCompany());

        data.put("email-customer", customer.getEmailadress());
        data.put("phone", customer.getMobile());
        data.put("hotel", customer.getHotelAdress());

        data.put("service-name", service.getServiceName());

        data.put("preferred-time", TIME_ONLY.format(booking.getTime()));
        data.put("duration", booking.getDuration().getText());
        data.put("flexibility", booking.getFlexibility().getText());
        data.put("start-date", DATE_ONLY.format(booking.getDate()));
        data.put("number-of-participants", booking.getAttendees().toString());

        data.put("location", booking.getLocation());
        data.put("meeting-location", booking.getLocation());
        data.put("remarks", booking.getRemark());

        BookingLinkEntity link = bookingLinkDAO.createBookingLink(booking, customer, provider);

        String fullLink = hostname + "/confirm-booking.xhtml?id=" + link.getId();
        data.put("link", fullLink);

        String formattedMailText = StrSubstitutor.replace(mailTextString, data);
        System.out.println(formattedMailText);

        emailSessionBean.sendEmail(provider.getEmailadress(), "Neue Buchungsanfrage auf the-eventers.de", formattedMailText);
    }

    public void setBooking(BookingTO booking) {
        this.booking = booking;
    }

    public BookingTO getBooking() {
        if (booking == null) booking = new BookingTO();
        return booking;
    }

    public List<EventDuration> getAllAvailableDurations() {
        return Arrays.asList(EventDuration.values());
    }

    public List<EventFlexibility> getAllAvailableFlexibilities() {
        return Arrays.asList(EventFlexibility.values());
    }

    public CustomerTO getCustomer() {
        if (customer == null) customer = new CustomerTO();
        return this.customer;
    }

    public String saveOffer() {
        booking = bookingDAO.persist(booking);
        return "booking2.xhtml?step=1&faces-redirect=true";
    }

    public String saveCustomer() {
        customer = customerDAO.persist(customer);
        return "booking3.xhtml?step=2&faces-redirect=true";
    }

    public ServiceEntity getService() {
        if (service == null) {
            service = serviceDAO.findServiceById(serviceId);
            if (service == null) throw new IllegalArgumentException("Unknown service id: " + serviceId);
        }
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public Greeting[] getAllAvailableGreetings() {
        return Greeting.values();
    }

    public String getPaymentUrl() {
        return hostname + paymentServletUrl;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void updateBooking(Payment payment) {

        getBooking().setPaymentStatus(PaymentStatus.PAYMENT_AUTHORIZED);
        getBooking().setPaypalPaymentId(payment.getId());

        booking = bookingDAO.persist(booking);

        LOGGER.info("Updated booking " + booking);
    }
}
