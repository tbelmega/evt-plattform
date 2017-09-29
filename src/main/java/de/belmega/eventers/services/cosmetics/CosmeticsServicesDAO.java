package de.belmega.eventers.services.cosmetics;

import de.belmega.eventers.services.massage.MassageServicesEntity;
import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class CosmeticsServicesDAO {

    @PersistenceContext
    EntityManager em;


    public void update(ProviderUserEntity provider, List<String> selectedCosmeticOffersByUser) {
        // Check if there is already an CosmeticsServicesEntity for this user in the database
        Optional<CosmeticsServicesEntity> cosmeticsServicesEntity = loadCosmeticsServicesEntityForUser(provider);

        if (cosmeticsServicesEntity.isPresent()) {
            // if yes, update that entity. JPA will store that to the database
            updateEntity(selectedCosmeticOffersByUser, cosmeticsServicesEntity.get());
        } else {
            //if no, create a new entity and store in the database
            createEntity(provider, selectedCosmeticOffersByUser);
        }
    }

    private void createEntity(ProviderUserEntity provider, List<String> selectedCosmeticOffersByUser) {
        CosmeticsServicesEntity entity = new CosmeticsServicesEntity(provider, selectedCosmeticOffersByUser);
        em.persist(entity);
    }

    private void updateEntity(List<String> selectedCosmeticOffersByUser, CosmeticsServicesEntity entity) {
        entity.setSelectedCosmeticOffersByUser(selectedCosmeticOffersByUser);
    }

    private Optional<CosmeticsServicesEntity> loadCosmeticsServicesEntityForUser(ProviderUserEntity provider) {
        // Prepare database query
        String qlString = "SELECT f FROM CosmeticsServicesEntity f JOIN f.provider p"
                + " JOIN FETCH f.selectedCosmeticOffersByUser " // tell JPA to load focusByUser *now* and not lazy. only required for Set properties
                + "WHERE p.id = :provider_id"; // tell JPA to load the MassageServicesEntity for the given user
        TypedQuery<CosmeticsServicesEntity> query = em.createQuery(qlString, CosmeticsServicesEntity.class);
        query.setParameter("provider_id", provider.getId());

        // Execute query; should find 0 to 1 results
        List<CosmeticsServicesEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }
}
