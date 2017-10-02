package de.belmega.eventers.booking;

import org.apache.commons.lang.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class BookingBean implements Serializable {


    private String title;
    private String category;
    private Booking booking;
    private Customer customer;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        switch (category) {
            case "entertainment":
                return "Unterhaltung";
            case "wellness":
                return "Wellness";
            case "fitness":
                return "Fitness";
            case "culinary":
                return "Kulinarisches";
            case "culture":
                return "Kultur";
            case "massage":
                return "Massage";
            case "transportation":
                return "Transport";
            default:
                return "Titel";
        }
    }

    public void setCategory(String category) {
        if (StringUtils.isNotEmpty(category))
            this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String submit() {
        return "http://the-eventers.de";
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        if (booking == null) booking = new Booking();
        return booking;
    }

    public List<String> getAllAvailableDurations() {
        return Arrays.asList(
                "30 Minuten",
                "1 Stunde",
                "1,5 Stunden",
                "2 Stunden",
                "2,5 Stunden",
                "3 Stunden",
                "4 Stunden",
                "5 Stunden");
    }

    public List<String> getAllAvailableFlexibilities() {
        return Arrays.asList(
                "nicht flexibel",
                "30 Minuten",
                "1 Stunde",
                "1,5 Stunden",
                "2 Stunden",
                "2,5 Stunden",
                "3 Stunden",
                "4 Stunden",
                "5 Stunden");
    }

    public Customer getCustomer() {
        if (customer == null) customer = new Customer();
        return this.customer;
    }

    public String saveOffer() {
        return "booking2.xhtml?step=1&faces-redirect=true";
    }

    public String saveCustomer() {
        return "booking3.xhtml?step=2&faces-redirect=true";
    }
}
