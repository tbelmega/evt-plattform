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


    public FitnessServicesDTO loadFitnessServicesDataForUser(ProviderUserEntity provider) {
        FitnessServicesEntity entity = getFitnessServicesEntity(provider);

        FitnessServicesDTO dto = new FitnessServicesDTO();
        dto.setOwnedEquipmentByUser(new HashSet<>(entity.getOwnedEquipmentByUser()));
        dto.setSelectedLocations(new HashSet<>(entity.getOfferedLocations()));
        dto.setSelectedServices(new HashSet<>(entity.getSelectedServices()));

        return dto;
    }

    private FitnessServicesEntity getFitnessServicesEntity(ProviderUserEntity provider) {
        String qlString = "SELECT f FROM FitnessServicesEntity f JOIN f.provider p"
                + " LEFT OUTER JOIN FETCH f.selectedServices " // tell JPA to load selectedServices *now* and not lazy. only required for Set properties
                + " LEFT OUTER JOIN FETCH f.offeredLocations "
                + " LEFT OUTER JOIN FETCH f.ownedEquipmentByUser "
                + "WHERE p.id = :provider_id"; // tell JPA to load the FitnessServicesEntity for the given user
        TypedQuery<FitnessServicesEntity> query = em.createQuery(qlString, FitnessServicesEntity.class);
        query.setParameter("provider_id", provider.getId());

        List<FitnessServicesEntity> resultList = query.getResultList();

        return getResultOrNewEntity(resultList, provider);
    }

    private FitnessServicesEntity getResultOrNewEntity(List<FitnessServicesEntity> resultList, ProviderUserEntity provider) {
        FitnessServicesEntity entity;

        if (resultList.isEmpty()) {
            entity = new FitnessServicesEntity();
            entity.setProvider(provider);
            em.persist(entity);
        } else {
            entity = resultList.get(0);
        }
        return entity;
    }

    public void update(ProviderUserEntity provider, FitnessServicesDTO data) {
        FitnessServicesEntity fitnessServicesEntity = getFitnessServicesEntity(provider);
        fitnessServicesEntity.setOfferedLocations(data.getSelectedLocations());
        fitnessServicesEntity.setOwnedEquipmentByUser(data.getOwnedEquipmentByUser());
        fitnessServicesEntity.setSelectedServices(data.getSelectedServices());
    }
}
