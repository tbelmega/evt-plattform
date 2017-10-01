package de.belmega.eventers.scheduling;

import de.belmega.eventers.user.UserID;
import org.primefaces.model.DefaultScheduleEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScheduleEventService {

    @Inject
    ScheduleEventDAO scheduleEventDAO;


    public void persistEvent(ScheduleEventEntity event) {
    scheduleEventDAO.persist(event);
    }

    public List<ScheduleEventEntity> findEventsByUser(UserID id) {
        return scheduleEventDAO.findEventsByUser(id);
    }

    public void deleteEvent(DefaultScheduleEvent eventId) {
        scheduleEventDAO.deleteEvent(eventId);
    }
}
