package de.belmega.eventers.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class CategoryDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(CategoryEntity wellness) {
        em.persist(wellness);
    }
}
