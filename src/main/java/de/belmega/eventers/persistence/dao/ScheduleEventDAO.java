package de.belmega.eventers.persistence.dao;

import de.belmega.eventers.dto.UserID;
import de.belmega.eventers.persistence.entities.ScheduleEventEntity;
import de.belmega.eventers.persistence.entities.ServiceProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

import static de.belmega.eventers.persistence.entities.ScheduleEventEntity.COLUMN_USER_ID;

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
}
