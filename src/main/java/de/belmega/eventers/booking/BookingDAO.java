package de.belmega.eventers.booking;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Transactional
public class BookingDAO {

    @PersistenceContext
    EntityManager em;

    public BookingTO persist(BookingTO booking) {

        BookingEntity bookingEntity = loadOrCreateBookingEntity(booking);

        bookingEntity.setNumberOfAttendees(booking.getAttendees());
        bookingEntity.setCreateInvoice(booking.getCreateInvoice());

        if (booking.getDate() != null && booking.getTime() != null) {
            Date dateTime = combineDateTime(booking.getDate(), booking.getTime());
            bookingEntity.setDateTime(dateTime);
        }

        bookingEntity.setDuration(booking.getDuration());
        bookingEntity.setFlexibility(booking.getFlexibility());
        bookingEntity.setLocation(booking.getLocation());
        bookingEntity.setRemark(booking.getRemark());

        booking.setId(bookingEntity.getId());
        return booking;
    }

    private BookingEntity loadOrCreateBookingEntity(BookingTO booking) {
        BookingEntity bookingEntity = getBookingEntity(booking);
        if (bookingEntity == null) {
            bookingEntity = new BookingEntity();
            em.persist(bookingEntity);
        }
        return bookingEntity;
    }

    private BookingEntity getBookingEntity(BookingTO booking) {
        if (booking.getId() != null) {
            return em.find(BookingEntity.class, booking.getId());
        } else return null;
    }

    private Date combineDateTime(Date date, Date time) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);
        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));

        return dateCal.getTime();
    }
}
