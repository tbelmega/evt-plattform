package de.belmega.eventers.booking;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
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
            case "entertainment": return "Unterhaltung";
            default: return "Titel";
        }
    }

    public void setCategory(String category) {
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
        return this.customer;
    }
}
