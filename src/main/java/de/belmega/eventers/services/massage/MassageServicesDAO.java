package de.belmega.eventers.services.massage;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by majab on 29.09.2017.
 */
@Transactional
public class MassageServicesDAO {

    @PersistenceContext
    EntityManager em;


    public void update(ProviderUserEntity provider, MassageServicesEntity entity) {
        em.merge(entity);
    }

    private MassageServicesEntity createEntity(ProviderUserEntity provider) {
        MassageServicesEntity entity = new MassageServicesEntity(provider, null, null);
        em.persist(entity);
        return entity;
    }


    public MassageServicesEntity loadMassageServicesEntityForUser(ProviderUserEntity provider) {
        // Prepare database query
        String qlString = "SELECT f FROM MassageServicesEntity f JOIN f.provider p "
                + "WHERE p.id = :provider_id"; // tell JPA to load the MassageServicesEntity for the given user
        TypedQuery<MassageServicesEntity> query = em.createQuery(qlString, MassageServicesEntity.class);
        query.setParameter("provider_id", provider.getId());

        // Execute query; should find 0 to 1 results
        List<MassageServicesEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return createEntity(provider);
        else return resultList.get(0);
    }
}
