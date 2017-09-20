package de.belmega.eventers.user;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

public class UserDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void persist(ProviderUserEntity entity) {
        em.persist(entity);
    }

    public Optional<ProviderUserEntity> findById(UserID serviceProviderID) {
        ProviderUserEntity user = em.find(ProviderUserEntity.class, serviceProviderID);
        return Optional.ofNullable(user);
    }

    public Optional<ProviderUserEntity> findByEmailAdress(String emailAdress) {
        String qlString = "SELECT u FROM ProviderUserEntity u WHERE u.emailadress = :emailadress";
        TypedQuery<ProviderUserEntity> query =
                em.createQuery(qlString, ProviderUserEntity.class);

        try {
            ProviderUserEntity result = query.setParameter("emailadress", emailAdress).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
