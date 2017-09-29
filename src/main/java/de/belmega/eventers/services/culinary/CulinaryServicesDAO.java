package de.belmega.eventers.services.culinary;

import de.belmega.eventers.services.fitness.FitnessServicesEntity;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class CulinaryServicesDAO {


    @PersistenceContext
    EntityManager em;


    public void update(ProviderUserEntity provider, List<CulinaryOfferSelection> selectedCulinaryOfferByUser) {

        for (CulinaryOfferSelection selection : selectedCulinaryOfferByUser) {

            // Check if there is already an CulinarySelection for this user in the database
            Optional<CulinarySelectionEntity> culinarySelectionEntity = loadCulinarySelectionEntityForUser(provider, selection.getId());

            if (culinarySelectionEntity.isPresent()) {
                // if yes, update that entity. JPA will store that to the database
                updateEntity(selection, culinarySelectionEntity.get());
            } else {
                //if no, create a new entity and store in the database
                createEntity(provider, selection);
            }
        }
    }

    private Optional<CulinarySelectionEntity> loadCulinarySelectionEntityForUser(ProviderUserEntity provider, String offerId) {
        String qlString = "SELECT f FROM CulinarySelectionEntity f JOIN f.provider p "
                + "WHERE p.id = :provider_id " // tell JPA to load the FitnessServicesEntity for the given user
                + "AND f.offerId = :offer_id";
        TypedQuery<CulinarySelectionEntity> query = em.createQuery(qlString, CulinarySelectionEntity.class);
        query.setParameter("provider_id", provider.getId());
        query.setParameter("offer_id", offerId);

        // Execute query; should find 0 to 1 results
        List<CulinarySelectionEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }

    private void createEntity(ProviderUserEntity provider, CulinaryOfferSelection selection) {
        CulinarySelectionEntity culinarySelectionEntity = new CulinarySelectionEntity(provider, selection.getId(), selection.isEnabled(), selection.getDescription());
        em.persist(culinarySelectionEntity);
    }

    /**
     * Update SelectionEntity with values from current user selection.
     */
    private void updateEntity(CulinaryOfferSelection selectedCulinaryOfferByUser, CulinarySelectionEntity culinarySelectionEntity) {
        culinarySelectionEntity.setEnabled(selectedCulinaryOfferByUser.isEnabled());
        culinarySelectionEntity.setDescription(selectedCulinaryOfferByUser.getDescription());
    }


}
