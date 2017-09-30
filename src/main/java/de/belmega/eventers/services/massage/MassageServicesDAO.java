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


    public void update(ProviderUserEntity provider, boolean massageTable, boolean chair) {
        // Check if there is already an MassageServicesEntity for this user in the database
        Optional<MassageServicesEntity> massageServicesEntity = loadMassageServicesEntityForUser(provider);

        if (massageServicesEntity.isPresent()) {
            // if yes, update that entity. JPA will store that to the database
            updateEntity(massageTable, chair, massageServicesEntity.get());
        } else {
            //if no, create a new entity and store in the database
            createEntity(provider, massageTable, chair);
        }
    }

    private void createEntity(ProviderUserEntity provider, boolean massageTable, boolean chair) {
        MassageServicesEntity entity = new MassageServicesEntity(provider, massageTable, chair);
        em.persist(entity);
    }

    private void updateEntity(boolean massageTable, boolean chair, MassageServicesEntity entity) {
        entity.setChair(chair);
        entity.setMassageTable(massageTable);
    }

    public Optional<MassageServicesEntity> loadMassageServicesEntityForUser(ProviderUserEntity provider) {
        // Prepare database query
        String qlString = "SELECT f FROM MassageServicesEntity f JOIN f.provider p "
                + "WHERE p.id = :provider_id"; // tell JPA to load the MassageServicesEntity for the given user
        TypedQuery<MassageServicesEntity> query = em.createQuery(qlString, MassageServicesEntity.class);
        query.setParameter("provider_id", provider.getId());

        // Execute query; should find 0 to 1 results
        List<MassageServicesEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }
}
