package de.belmega.eventers.scheduling;

import de.belmega.eventers.user.UserID;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class ScheduleEventDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ScheduleEventEntity entity) {
        em.persist(entity);
    }


    public List<ScheduleEventEntity> findEventsByUser(UserID id) {
        String qlString = "SELECT e FROM ScheduleEventEntity e JOIN e.user u WHERE u.id = :id";
        TypedQuery<ScheduleEventEntity> query =
                em.createQuery(qlString, ScheduleEventEntity.class);
        List<ScheduleEventEntity> results = query.setParameter("id", id).getResultList();

        return results;
    }


    public void deleteEvent(String eventId) {
        ScheduleEventEntity eventEntity = em.find(ScheduleEventEntity.class, eventId);
        em.remove(eventEntity);
    }
}
