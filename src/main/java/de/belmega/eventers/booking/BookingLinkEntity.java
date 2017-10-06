package de.belmega.eventers.booking;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.*;

@Entity
@Table(name = "BookingLink")
public class BookingLinkEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Long customerId;

    @ManyToOne
    private ProviderUserEntity provider;

    public BookingLinkEntity(String id) {
        this.id = id;
    }

    public BookingLinkEntity() {
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
