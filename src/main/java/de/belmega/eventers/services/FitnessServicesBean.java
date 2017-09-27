package de.belmega.eventers.services;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@SessionScoped
public class FitnessServicesBean {

    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    private OfferedServicesDAO offeredServicesDAO;


    public List<String> getOfferedServicesByUser() {
        ProviderUserEntity provider = userProfileBean.getProvider();
        return offeredServicesDAO.findServicesForProvider(provider);
    }

    public void setOfferedServicesByUser(List<String> selected) {
        System.out.println("Selected Services: " + selected);
    }

    public List<String> getAllAvailableServices() {
        ProviderUserEntity provider = userProfileBean.getProvider();
        return offeredServicesDAO.findServices(ServiceType.FITNESS);
    }

    public Object getOfferedLocationsByUser() {
        ProviderUserEntity provider = userProfileBean.getProvider();
        return offeredServicesDAO.findLocationsByUser(provider);
    }

    public Object getAllAvailableLocations() {
        return offeredServicesDAO.findLocations(ServiceType.FITNESS);
    }

    public Object getOwnedEquipmentByUser() {
        ProviderUserEntity provider = userProfileBean.getProvider();
        return offeredServicesDAO.findEquipmentByUser(provider);
    }

    public Object getAllAvailableEquipment() {
        return offeredServicesDAO.findEquipment(ServiceType.FITNESS);
    }
}
