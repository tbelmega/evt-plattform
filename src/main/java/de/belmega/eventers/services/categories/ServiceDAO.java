package de.belmega.eventers.services.categories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ServiceDAO {

    @PersistenceContext
    EntityManager em;

    public void persist(ServiceEntity massage) {
        em.persist(massage);
    }

    public List<ServiceEntity> findServicesByCategory(String categoryId) {

        String qlString = "SELECT s FROM ServiceEntity s JOIN s.category c WHERE c.id = :category_id";
        TypedQuery<ServiceEntity> query =
                em.createQuery(qlString, ServiceEntity.class);

        return query.setParameter("category_id", categoryId).getResultList();
    }

    public ServiceEntity findServiceById(String serviceId) {
        return em.find(ServiceEntity.class, serviceId);
    }
}
