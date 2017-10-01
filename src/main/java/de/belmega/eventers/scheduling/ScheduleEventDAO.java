package de.belmega.eventers.scheduling;

import de.belmega.eventers.TestDataGenerator;
import de.belmega.eventers.user.UserID;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScheduleEventDAO {

    @PersistenceContext
    EntityManager em;


    private static final Logger LOG = Logger.getLogger(ScheduleEventDAO.class);


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


    public void deleteEvent(DefaultScheduleEvent event) {
        String qlString = "SELECT e FROM ScheduleEventEntity e WHERE e.startDate = :startDate " +
                "AND e.endDate = :endDate AND e.title = :title";
        TypedQuery<ScheduleEventEntity> query =
                em.createQuery(qlString, ScheduleEventEntity.class);
        List<ScheduleEventEntity> results = query
                .setParameter("startDate", event.getStartDate())
                .setParameter("endDate", event.getEndDate())
                .setParameter("title", event.getTitle())
                .getResultList();

        if (results.isEmpty()) LOG.warn("Attempted to delete ScheduleEvent that does not exist.");

        for (ScheduleEventEntity result: results) {
            em.remove(result);
        }
    }
}
