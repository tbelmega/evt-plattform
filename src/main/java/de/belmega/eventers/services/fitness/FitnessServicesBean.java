package de.belmega.eventers.services.fitness;

import de.belmega.eventers.services.ServiceType;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@SessionScoped
public class FitnessServicesBean {

    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    private FitnessServicesDAO fitnessServicesDAO;

    private Set<String> selectedServices;
    private Set<String> offeredLocations;
    private Set<String> ownedEquipmentByUser;

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }

    public Set<String> getOfferedServicesByUser() {
        // Optional<FitnessServicesEntity> means: maybe there is a FitnessServicesEntity, may there is none. Optional "wraps" a FitnessServicesEntity there.
        // when the user didn't fill out the form yet,
        // there is no FitnessServicesEntity for him in the database, then the Optional<FitnessServicesEntity> is empty
        Optional<FitnessServicesEntity> fitnessServicesEntity = fitnessServicesDAO.loadFitnessServicesEntityForUser(getProvider());

        // If there is a FitnessServicesEntity for the user, return it's services selection
        if (fitnessServicesEntity.isPresent())
            return fitnessServicesEntity.get().getSelectedServices(); // Optional<Foo>.get() gets the Foo-object that is wrapped by the Optional
        else return new HashSet<>();
    }

    public void setOfferedServicesByUser(Set<String> selected) {
        this.selectedServices = selected;
    }

    public Set<String> getAllAvailableServices() {
        return fitnessServicesDAO.findServices(ServiceType.FITNESS);
    }

    public Set<String> getOfferedLocationsByUser() {
        Optional<FitnessServicesEntity> fitnessServicesEntity = fitnessServicesDAO.loadFitnessServicesEntityForUser(getProvider());
        if (fitnessServicesEntity.isPresent()) return fitnessServicesEntity.get().getOfferedLocations();
        else return new HashSet<>();
    }

    public void setOfferedLocationsByUser(Set<String> offeredLocationsByUser) {
        this.offeredLocations = offeredLocationsByUser;
    }

    public Set<String> getOwnedEquipmentByUser() {
        Optional<FitnessServicesEntity> fitnessServicesEntity = fitnessServicesDAO.loadFitnessServicesEntityForUser(getProvider());
        if (fitnessServicesEntity.isPresent()) return fitnessServicesEntity.get().getOwnedEquipmentByUser();
        else return new HashSet<>();
    }

    public void setOwnedEquipmentByUser(Set<String> ownedEquipmentByUser) {
        this.ownedEquipmentByUser = ownedEquipmentByUser;
    }

    public Set<String> getAllAvailableEquipment() {
        return fitnessServicesDAO.findEquipment(ServiceType.FITNESS);
    }

    public Set<String> getAllAvailableLocations() {
        return fitnessServicesDAO.findLocations(ServiceType.FITNESS);
    }

    /**
     * When the user presses "save" on the screen, the JSF framework calls all the Setters (setOfferedServicesByUser, setOfferedLocationsByUser, setOwnedEquipmentByUser)
     * to set the user-selected values to this FitnessServicesBean.
     * Then the save() method is called, where we can take these newly set values and write them to the database:
     */
    public void save() {
        fitnessServicesDAO.update(getProvider(), selectedServices, offeredLocations, ownedEquipmentByUser);
    }
}
