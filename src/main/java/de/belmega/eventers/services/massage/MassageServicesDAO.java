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


    public void update(ProviderUserEntity provider, List<String> focusByUser, boolean massageTable, boolean chair) {
        // Check if there is already an MassageServicesEntity for this user in the database
        Optional<MassageServicesEntity> massageServicesEntity = loadMassageServicesEntityForUser(provider);

        if (massageServicesEntity.isPresent()) {
            // if yes, update that entity. JPA will store that to the database
            updateEntity(focusByUser, massageTable, chair, massageServicesEntity.get());
        } else {
            //if no, create a new entity and store in the database
            createEntity(provider, focusByUser, massageTable, chair);
        }
    }


    private void createEntity(ProviderUserEntity provider, List<String> focusByUser, boolean massageTable, boolean chair) {
        MassageServicesEntity entity = new MassageServicesEntity(provider, focusByUser, massageTable, chair);
        em.persist(entity);
        System.out.println("MassageServicesEntity created."); // Remove later. Is just to check if code was executed.
    }

    private void updateEntity(List<String> focusByUser, boolean massageTable, boolean chair, MassageServicesEntity entity) {
        entity.setChair(chair);
        entity.setMassageTable(massageTable);
        entity.setFocusByUser(focusByUser);
        System.out.println("MassageServicesEntity updated.");
    }

    public Optional<MassageServicesEntity> loadMassageServicesEntityForUser(ProviderUserEntity provider) {
        // Prepare database query
        String qlString = "SELECT f FROM MassageServicesEntity f JOIN f.provider p"
                + " JOIN FETCH f.focusByUser " // tell JPA to load focusByUser *now* and not lazy. only required for Set properties
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
