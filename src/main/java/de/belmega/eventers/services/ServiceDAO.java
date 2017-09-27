package de.belmega.eventers.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class ServiceDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ServiceEntity massage) {
        em.persist(massage);
    }
}
