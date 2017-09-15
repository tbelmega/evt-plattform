package de.belmega.eventers.persistence.dao;

import de.belmega.eventers.dto.UserID;
import de.belmega.eventers.persistence.entities.ServiceProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class ProviderDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ServiceProviderUserEntity entity) {
        em.persist(entity);
    }

    public ServiceProviderUserEntity findById(UserID serviceProviderID) {
        return em.find(ServiceProviderUserEntity.class, serviceProviderID);
    }
}
