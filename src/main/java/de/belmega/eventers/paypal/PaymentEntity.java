package de.belmega.eventers.paypal;

import de.belmega.eventers.booking.BookingEntity;
import de.belmega.eventers.booking.PaymentStatus;

import javax.persistence.*;

@Entity
@Table(name = "Payment")
public class PaymentEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private BookingEntity booking;

    private PaymentStatus paymentStatus = PaymentStatus.NONE;
    private String paypalPaymentId;
    private String authorizationId;

    public long getId() {
        return id;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }
}
