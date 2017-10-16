package de.belmega.eventers.booking;

import com.paypal.base.rest.PayPalRESTException;
import de.belmega.eventers.mail.EmailSessionBean;
import de.belmega.eventers.paypal.PaypalService;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class ConfirmBookingBean implements Serializable {

    @Inject
    private BookingLinkDAO bookingLinkDAO;

    @Inject
    private CustomerDAO customerDAO;

    @Inject
    private BookingDAO bookingDAO;

    @Inject
    private EmailSessionBean emailSessionBean;

    @Inject
    private PaypalService paypalService;

    @Inject
    @ConfigurationValue("urls.pages.login")
    String loginPage;


    private String linkId;
    private BookingLinkEntity link;
    private CustomerEntity customerEntity;
    private BookingEntity bookingEntity;
    private String remarks;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public BookingLinkEntity getLink() {
        if (link == null) link = findLink();

        return link;
    }

    private BookingLinkEntity findLink() {
        BookingLinkEntity linkById = bookingLinkDAO.findLinkById(linkId);

        if (linkById == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dieser Link ist ungültig.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return linkById;
    }

    public void setLink(BookingLinkEntity link) {
        this.link = link;
    }

    public CustomerEntity getCustomerEntity() {
        if (customerEntity == null) loadCustomerEntity();
        return customerEntity;
    }

    private void loadCustomerEntity() {
        BookingLinkEntity link = getLink();
        if (link != null)
            customerEntity = customerDAO.findById(link.getCustomerId());
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public BookingEntity getBookingEntity() {
        if (bookingEntity == null)  loadBookingEntity();
        return bookingEntity;
    }

    private void loadBookingEntity() {
        BookingLinkEntity link = getLink();
        if (link != null)
            bookingEntity = bookingDAO.findById(link.getBookingId());
    }

    public void setBookingEntity(BookingEntity bookingEntity) {
        this.bookingEntity = bookingEntity;
    }

    public String confirm(){

        if (bookingEntity.isAccepted()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Anfrage nicht mehr verfügbar",
                    "Diese Anfrage wurde leider bereits von einem anderen Anbieter bestätigt.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {

            bookingEntity.setProvider(link.getProvider());
            bookingDAO.update(bookingEntity);
            emailSessionBean.sendEmail(customerEntity.getEmailadress(), "Ihre Buchung auf the-eventers.de wurde bestätigt",
                    "blubb");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Buchungsanfrage angenommen",
                    "Der Kunde wird über Ihre Zusage informiert.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return loginPage;
    };

    public String reject() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Buchungsanfrage abgelehnt", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return loginPage;
    }

    public String confirmEvent() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vielen Dank für Ihre Bestätigung.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        try {
            paypalService.captureAuthorizedPayment(this.link.getBookingId());
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        getBookingEntity().setProviderRemark(remarks);

        return loginPage;
    }

    public String cancelEvent() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vielen Dank für Ihre Rückmeldung, " +
                "dass das Event abgesagt wurde.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        getBookingEntity().setProviderRemark(remarks);

        return loginPage;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }
}
