package de.belmega.eventers.booking;

import de.belmega.eventers.scheduling.ScheduleEventDAO;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.services.categories.ServiceDAO;
import de.belmega.eventers.services.categories.ServiceEntity;
import org.apache.commons.lang.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class BookingBean implements Serializable {

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private BookingDAO bookingDAO;

    @Inject
    private CustomerDAO customerDAO;

    private String serviceId;
    private BookingTO booking;
    private CustomerTO customer;
    private ServiceEntity service;

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

        for (ScheduleEventEntity availability: availabilities)
            System.out.println(availability);

        return "booking4.xhtml&faces-redirect=true";
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
}
