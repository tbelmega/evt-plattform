package de.belmega.eventers.booking;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public class BookingLinkDAO {


    @PersistenceContext
    EntityManager em;

    public BookingLinkEntity createBookingLink(BookingTO booking, CustomerTO customer, ProviderUserEntity provider) {

        BookingLinkEntity entity = new BookingLinkEntity(UUID.randomUUID().toString());
        entity.setBookingId(booking.getId());
        entity.setCustomerId(customer.getId());
        entity.setProvider(provider);

        em.persist(entity);

        return entity;
    }

    public BookingLinkEntity findLinkById(String linkId) {
        return em.find(BookingLinkEntity.class, linkId);
    }

}
