package de.belmega.eventers.services.common;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.services.categories.ServiceDAO;
import de.belmega.eventers.services.categories.ServiceEntity;
import de.belmega.eventers.user.ProviderUserEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
public class SelectionServicesDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ServiceDAO serviceDAO;

    public void update(ProviderUserEntity provider, List<OfferSelection> usersEntertainmentOfferSelections) {

        for (OfferSelection selection : usersEntertainmentOfferSelections) {

            // Check if there is already a SelectionEntity for this user in the database
            Optional<SelectionEntity> entertainmentSelectionEntity = loadSelectionEntityForUser(provider, selection.getServiceId());

            if (entertainmentSelectionEntity.isPresent()) {
                // if yes, update that entity. JPA will store that to the database
                updateEntity(selection, entertainmentSelectionEntity.get());
            } else {
                //if no, create a new entity and store in the database
                createSelectionEntity(provider, selection.getServiceId(), selection.isEnabled(), selection.getDescription(), selection.getCategoryName());
            }
        }
    }

    private Optional<SelectionEntity> loadSelectionEntityForUser(ProviderUserEntity provider, String offerId) {
        String qlString = "SELECT f FROM SelectionEntity f JOIN f.provider p "
                + "WHERE p.id = :provider_id " // tell JPA to load the SelectionEntity for the given user
                + "AND f.offerId = :offer_id";
        TypedQuery<SelectionEntity> query = em.createQuery(qlString, SelectionEntity.class);
        query.setParameter("provider_id", provider.getId());
        query.setParameter("offer_id", offerId);

        // Execute query; should find 0 to 1 results
        List<SelectionEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }

    private void updateEntity(OfferSelection selection, SelectionEntity entity) {
        entity.setEnabled(selection.isEnabled());
        entity.setDescription(selection.getDescription());
        entity.setCategoryName(selection.getCategoryName());
    }

    /**
     * Get the OfferSelection values for the given user and categoryId.
     * Represents what the user selected / entered and saved to database.
     */
    public List<OfferSelection> findSelectionsForUser(ProviderUserEntity provider, ServiceCategoryId categoryId) {

        List<ServiceEntity> servicesByCategory = serviceDAO.findServicesByCategory(categoryId.name());

        List<OfferSelection> selections = new ArrayList<>();
        for (ServiceEntity service : servicesByCategory) {
            SelectionEntity selectionEntity = getSelectionEntity(provider, categoryId, service);

            // Combine loaded data of ServiceEntity and SelectionEntity to OfferSelection object
            selections.add(
                    new OfferSelection(
                            categoryId.name(),
                            service.getServiceName(),
                            service.getServiceName(),
                            selectionEntity.isEnabled(),
                            selectionEntity.getDescription()
                    )
            );
        }

        return selections;
    }

    private SelectionEntity getSelectionEntity(ProviderUserEntity provider, ServiceCategoryId category, ServiceEntity service) {
        String serviceName = service.getServiceName();
        Optional<SelectionEntity> selectionEntity = loadSelectionEntityForUser(provider, serviceName);

        if (selectionEntity.isPresent())
            return selectionEntity.get();
        else
            return createSelectionEntity(provider, serviceName, false, "", category.name());
    }

    private SelectionEntity createSelectionEntity(ProviderUserEntity provider, String serviceName, boolean enabled, String description, String name) {
        SelectionEntity entity = new SelectionEntity(
                provider,
                serviceName,
                enabled,
                description,
                name
        );
        em.persist(entity);
        return entity;
    }
}
