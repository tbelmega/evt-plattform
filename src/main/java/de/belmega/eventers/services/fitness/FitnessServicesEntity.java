package de.belmega.eventers.services.fitness;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * This entity represents all data which the user can enter on fitness.xhtml page.
 * <p>
 * Entity classes have only constructors and getter/setter methods.
 * The only interesting things are the field declaratons on top, not the methods.
 */
@Entity
public class FitnessServicesEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne // every user has only one FitnessServicesEntity associated with it.
    private ProviderUserEntity provider;

    @ElementCollection(targetClass = String.class)
    private Set<String> selectedServices;

    @ElementCollection(targetClass = String.class)
    private Set<String> offeredLocations;

    @ElementCollection(targetClass = String.class)
    private Set<String> ownedEquipmentByUser;


    public FitnessServicesEntity(ProviderUserEntity provider, Set<String> selectedServices, Set<String> offeredLocations, Set<String> ownedEquipmentByUser) {
        this.provider = provider;
        this.selectedServices = selectedServices;
        this.offeredLocations = offeredLocations;
        this.ownedEquipmentByUser = ownedEquipmentByUser;
    }

    public FitnessServicesEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public Set<String> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(Set<String> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public Set<String> getOfferedLocations() {
        return offeredLocations;
    }

    public void setOfferedLocations(Set<String> offeredLocations) {
        this.offeredLocations = offeredLocations;
    }

    public Set<String> getOwnedEquipmentByUser() {
        return ownedEquipmentByUser;
    }

    public void setOwnedEquipmentByUser(Set<String> ownedEquipmentByUser) {
        this.ownedEquipmentByUser = ownedEquipmentByUser;
    }
}
