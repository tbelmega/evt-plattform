package de.belmega.eventers.booking;

import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
public class BookingDAO {

    @PersistenceContext
    EntityManager em;

    public BookingTO persist(BookingTO booking) {

        BookingEntity bookingEntity = loadOrCreateBookingEntity(booking);

        bookingEntity.setNumberOfAttendees(booking.getAttendees());
        bookingEntity.setCreateInvoice(booking.getCreateInvoice());

        if (booking.getDate() != null && booking.getTime() != null) {
            Date dateTime = DateUtil.combineDateTime(booking.getDate(), booking.getTime());
            bookingEntity.setPreferredStartTime(dateTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.MINUTE, booking.getFlexibility().getMinutes());
            bookingEntity.setLatestStartTime(calendar.getTime());

            calendar.setTime(dateTime);
            calendar.add(Calendar.MINUTE, booking.getDuration().getMinutes());
            bookingEntity.setEarliestEndTime(calendar.getTime());
        }

        bookingEntity.setDuration(booking.getDuration());
        bookingEntity.setFlexibility(booking.getFlexibility());
        bookingEntity.setLocation(booking.getLocation());
        bookingEntity.setRemark(booking.getRemark());

        booking.setId(bookingEntity.getId());
        return booking;
    }

    private BookingEntity loadOrCreateBookingEntity(BookingTO booking) {
        if (booking.getId() == null) {
            BookingEntity bookingEntity = new BookingEntity();
            em.persist(bookingEntity);
            return bookingEntity;
        }
        else return getBookingEntity(booking);
    }

    private BookingEntity getBookingEntity(BookingTO booking) {
        if (booking.getId() == null) throw new IllegalArgumentException("Cannot load Booking with ID null.");

        return em.find(BookingEntity.class, booking.getId());
    }


    public List<ScheduleEventEntity> findMatchingAvailabilities(BookingTO booking) {
        BookingEntity bookingEntity = getBookingEntity(booking);

        String qlString = "SELECT e FROM ScheduleEventEntity e JOIN e.user u "
                + "WHERE e.startDate <= :latestStartTime AND e.endDate >= :earliertEndTime";
        TypedQuery<ScheduleEventEntity> query =
                em.createQuery(qlString, ScheduleEventEntity.class);
        query.setParameter("latestStartTime", bookingEntity.getLatestStartTime())
                .setParameter("earliertEndTime", bookingEntity.getEarliestEndTime());

        return query.getResultList();
    }

    public BookingEntity findById(Long bookingId) {
        return em.find(BookingEntity.class, bookingId);
    }
}
