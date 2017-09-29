package de.belmega.eventers.services.fitness;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class FitnessServicesDAO {

    @PersistenceContext
    EntityManager em;

    // TODO: move to database. hard-coded values for testing only
    public Set<String> findServices(ServiceCategoryId fitness) {
        return new HashSet<>(Arrays.asList(
                "Training als Personal Coach in einem Sportstudio",
                "Personal Coach Laufen / Joggen im Park",
                "Personal Coach Nordic Walking im Park",
                "Personal Coach in einer Kletterhalle / Klettergarten",
                "Yoga / Tai Chi",
                "Pilates",
                "Begleitung als Trainer / Schiedsrichter / Spieler bei Indoor Fußball"));
    }

    // TODO: move to database. hard-coded values for testing only
    public Set<String> findLocations(ServiceCategoryId fitness) {
        return new HashSet<>(Arrays.asList(
                "Indoor", "Outdoor", "Gym", "Hotel"));
    }

    // TODO: move to database. hard-coded values for testing only
    public Set<String> findEquipment(ServiceCategoryId fitness) {
        return new HashSet<>(Arrays.asList(
                "Nordic Walking Stöcke", "Isomatten", "Federballsets", "Walking Hanteln"));
    }

    public void update(ProviderUserEntity provider, Set<String> selectedServices, Set<String> offeredLocations, Set<String> ownedEquipmentByUser) {

        // Check if there is already an FitnessServicesEntity for this user in the database
        Optional<FitnessServicesEntity> fitnessServicesEntity = loadFitnessServicesEntityForUser(provider);

        if (fitnessServicesEntity.isPresent()) {
            // if yes, update that entity. JPA will store that to the database
            updateEntity(selectedServices, offeredLocations, ownedEquipmentByUser, fitnessServicesEntity.get());
        } else {
            //if no, create a new entity and store in the database
            createEntity(provider, selectedServices, offeredLocations, ownedEquipmentByUser);
        }

    }


    private void createEntity(ProviderUserEntity provider, Set<String> selectedServices, Set<String> offeredLocations, Set<String> ownedEquipmentByUser) {
        FitnessServicesEntity entity = new FitnessServicesEntity(provider, selectedServices, offeredLocations, ownedEquipmentByUser);
        em.persist(entity);
        System.out.println("FitnessServicesEntity created."); // Remove later. Is just to check if code was executed.
    }

    private void updateEntity(Set<String> selectedServices, Set<String> offeredLocations, Set<String> ownedEquipmentByUser, FitnessServicesEntity entity) {
        entity.setSelectedServices(selectedServices);
        entity.setOfferedLocations(offeredLocations);
        entity.setOwnedEquipmentByUser(ownedEquipmentByUser);
        System.out.println("FitnessServicesEntity updated.");
    }

    public Optional<FitnessServicesEntity> loadFitnessServicesEntityForUser(ProviderUserEntity provider) {
        // Prepare database query
        String qlString = "SELECT f FROM FitnessServicesEntity f JOIN f.provider p"
                + " JOIN FETCH f.selectedServices " // tell JPA to load selectedServices *now* and not lazy. only required for Set properties
                + " JOIN FETCH f.offeredLocations "
                + " JOIN FETCH f.ownedEquipmentByUser "
                + "WHERE p.id = :provider_id"; // tell JPA to load the FitnessServicesEntity for the given user
        TypedQuery<FitnessServicesEntity> query = em.createQuery(qlString, FitnessServicesEntity.class);
        query.setParameter("provider_id", provider.getId());

        // Execute query; should find 0 to 1 results
        List<FitnessServicesEntity> resultList = query.getResultList();

        // Return either an empty value or the found result
        if (resultList.isEmpty()) return Optional.empty();
        else return Optional.of(resultList.get(0));
    }
}
