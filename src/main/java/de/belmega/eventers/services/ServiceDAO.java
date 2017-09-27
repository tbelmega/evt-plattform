package de.belmega.eventers.services;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class ServiceDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ServiceEntity massage) {
        em.persist(massage);
    }

    public List<ServiceEntity> findServicesByCategory(String categoryName) {

        String qlString = "SELECT s FROM ServiceEntity s JOIN s.category c WHERE c.categoryName = :category_name";
        TypedQuery<ServiceEntity> query =
                em.createQuery(qlString, ServiceEntity.class);

        return query.setParameter("category_name", categoryName).getResultList();
    }
}
