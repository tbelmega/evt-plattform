package de.belmega.eventers.services.fitness;

import java.util.Set;

public class FitnessServicesDTO {

    private Set<String> selectedServices;
    private Set<String> selectedLocations;
    private Set<String> ownedEquipmentByUser;

    public Set<String> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(Set<String> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public Set<String> getSelectedLocations() {
        return selectedLocations;
    }

    public void setSelectedLocations(Set<String> selectedLocations) {
        this.selectedLocations = selectedLocations;
    }

    public Set<String> getOwnedEquipmentByUser() {
        return ownedEquipmentByUser;
    }

    public void setOwnedEquipmentByUser(Set<String> ownedEquipmentByUser) {
        this.ownedEquipmentByUser = ownedEquipmentByUser;
    }
}
