package de.belmega.eventers.services.transport;

import de.belmega.eventers.services.common.OfferSelection;
import de.belmega.eventers.services.common.SelectionServicesDAO;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;


/**
 * Created by majab on 26.09.2017.
 */
@ManagedBean
@SessionScoped
public class TransportOffersServicesBean {

    @Inject
    private SelectionServicesDAO selectionServicesDAO;

    @Inject
    private UserProfileBean userProfileBean;

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }


    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOfferSelection
    private List<OfferSelection> usersTransportOfferSelections = Arrays.asList(
            new OfferSelection("taxi-service", "Taxi-Service"),
            new OfferSelection("limousine-service", "Limosinen-Service"),
            new OfferSelection("velo-taxi", "Velotaxi-Service"),
            new OfferSelection("bicycle-rentals", "Fahrradverleih"));



    public void setUsersTransportOfferSelections(List<OfferSelection> allAvailableCulturalOffers) {
        this.usersTransportOfferSelections = allAvailableCulturalOffers;
    }

    public List<OfferSelection> getUsersTransportOfferSelections() {
        return usersTransportOfferSelections;
    }

    public String getVisibility(OfferSelection offer) {
        return offer.isEnabled() ? "" : "invisible"; // returns either nothing or the CSS-Class "invisible"
    }


    public void save() {
        selectionServicesDAO.update(getProvider(), usersTransportOfferSelections);
    }
}
