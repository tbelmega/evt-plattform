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

public class ScheduleEventDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ScheduleEventEntity entity) {
        em.persist(entity);
    }


    public List<ScheduleEventEntity> findEventsByUser(UserID id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ScheduleEventEntity> query = cb.createQuery(ScheduleEventEntity.class);

        Root<ScheduleEventEntity> from = query.from(ScheduleEventEntity.class);
        query.select(from).where(cb.equal(from.get("userId"), id));

        return em.createQuery(query).getResultList();
    }
}
