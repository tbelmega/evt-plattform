package de.belmega.eventers.services.fitness;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ManagedBean
@RequestScoped
public class FitnessServicesBean {

    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    private FitnessServicesDAO fitnessServicesDAO;

    private FitnessServicesDTO data;

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }

    public Set<String> getAllAvailableServices() {
        return fitnessServicesDAO.findServices(ServiceCategoryId.SPORTS);
    }

    public Set<String> getAllAvailableEquipment() {
        return fitnessServicesDAO.findEquipment(ServiceCategoryId.SPORTS);
    }

    public Set<String> getAllAvailableLocations() {
        return fitnessServicesDAO.findLocations(ServiceCategoryId.SPORTS);
    }

    /**
     * When the user presses "save" on the screen, the JSF framework calls all the Setters (setOfferedServicesByUser, setOfferedLocationsByUser, setOwnedEquipmentByUser)
     * to set the user-selected values to this FitnessServicesBean.
     * Then the save() method is called, where we can take these newly set values and write them to the database:
     */
    public void save() {
        fitnessServicesDAO.update(getProvider(), data);
    }


    public FitnessServicesDTO getData() {
        if (data == null) data = fitnessServicesDAO.loadFitnessServicesDataForUser(getProvider());
        return data;
    }

    public void setData(FitnessServicesDTO data) {
        this.data = data;
    }
}
