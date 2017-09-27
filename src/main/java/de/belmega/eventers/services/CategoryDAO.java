package de.belmega.eventers.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public class CategoryDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(CategoryEntity wellness) {
        em.persist(wellness);
    }

    /**
     * Find all CategoryEntities from database
     */
    public List<CategoryEntity> findAll() {

        // Boiler plate code... always the same three lines, just replace the class
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> rootEntry = cq.from(CategoryEntity.class);

        // Create the query
        CriteriaQuery<CategoryEntity> all = cq.select(rootEntry);
        TypedQuery<CategoryEntity> allQuery = em.createQuery(all);

        // Execute the query to get the result list
        List<CategoryEntity> resultList = allQuery.getResultList();
        return resultList;
    }
}
