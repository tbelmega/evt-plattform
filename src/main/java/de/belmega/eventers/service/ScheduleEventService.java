package de.belmega.eventers.service;

import de.belmega.eventers.dto.UserID;
import de.belmega.eventers.persistence.dao.ScheduleEventDAO;
import de.belmega.eventers.persistence.entities.ScheduleEventEntity;

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
}
