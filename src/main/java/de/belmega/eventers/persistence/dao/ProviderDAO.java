package de.belmega.eventers.persistence.dao;

import de.belmega.eventers.dto.ServiceProviderID;
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

    public ServiceProviderUserEntity findById(ServiceProviderID serviceProviderID) {
        return em.find(ServiceProviderUserEntity.class, serviceProviderID);
    }
}