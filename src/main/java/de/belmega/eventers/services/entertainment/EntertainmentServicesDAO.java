package de.belmega.eventers.services.entertainment;

import de.belmega.eventers.services.culinary.CulinaryOfferSelection;
import de.belmega.eventers.services.culinary.CulinarySelectionEntity;
import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class EntertainmentServicesDAO {

    @PersistenceContext
    EntityManager em;

    public void update(ProviderUserEntity provider, List<EntertainmentOfferSelection> usersEntertainmentOfferSelections) {

        for (EntertainmentOfferSelection selection : usersEntertainmentOfferSelections) {

            // Check if there is already an CulinarySelection for this user in the database
            Optional<EntertainmentSelectionEntity> entertainmentSelectionEntity = loadSelectionEntityForUser(provider, selection.getId());

            if (entertainmentSelectionEntity.isPresent()) {
                // if yes, update that entity. JPA will store that to the database
                updateEntity(selection, entertainmentSelectionEntity.get());
            } else {
                //if no, create a new entity and store in the database
                createEntity(provider, selection);
            }
        }
    }

    private Optional<EntertainmentSelectionEntity> loadSelectionEntityForUser(ProviderUserEntity provider, String id) {
        String qlString = "SELECT f FROM EntertainmentSelectionEntity f JOIN f.provider p "
                + "WHERE p.id = :provider_id " // tell JPA to load the EntertainmentSelectionEntity for the given user
                + "AND f.offerId = :offer_id";
        TypedQuery<EntertainmentSelectionEntity> query = em.createQuery(qlString, EntertainmentSelectionEntity.class);
        query.setParameter("provider_id", provider.getId());
        query.setParameter("offer_id", id);

        // Execute query; should find 0 to 1 results
        List<EntertainmentSelectionEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }

    private void updateEntity(EntertainmentOfferSelection selection, EntertainmentSelectionEntity entity) {
        entity.setEnabled(selection.isEnabled());
        entity.setDescription(selection.getDescription());
    }

    private void createEntity(ProviderUserEntity provider, EntertainmentOfferSelection selection) {
        EntertainmentSelectionEntity entity = new EntertainmentSelectionEntity(provider, selection.getId(), selection.isEnabled(), selection.getDescription());
        em.persist(entity);
    }
}
