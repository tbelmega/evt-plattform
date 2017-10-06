package de.belmega.eventers.booking;

import de.belmega.eventers.mail.EmailSessionBean;
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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
public class ConfirmBookingBean implements Serializable {

    @Inject
    private BookingLinkDAO bookingLinkDAO;

    @Inject
    private CustomerDAO customerDAO;

    @Inject
    private BookingDAO bookingDAO;

    private String linkId;
    private BookingLinkEntity link;
    private CustomerEntity customerEntity;
    private BookingEntity bookingEntity;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public BookingLinkEntity getLink() {
        if (link == null) link = bookingLinkDAO.findLinkById(linkId);
        return link;
    }

    public void setLink(BookingLinkEntity link) {
        this.link = link;
    }

    public CustomerEntity getCustomerEntity() {
        if (customerEntity == null) customerEntity = customerDAO.findById(link.getCustomerId());
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public BookingEntity getBookingEntity() {
        if (bookingEntity == null) bookingEntity = bookingDAO.findById(link.getBookingId());
        return bookingEntity;
    }

    public void setBookingEntity(BookingEntity bookingEntity) {
        this.bookingEntity = bookingEntity;
    }
}
