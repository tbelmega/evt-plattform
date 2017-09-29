package de.belmega.eventers.services.entertainment;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by majab on 26.09.2017.
 */
@ManagedBean
@SessionScoped
public class EntertainmentServicesBean {

    @Inject
    private EntertainmentServicesDAO entertainmentServicesDAO;

    @Inject
    private UserProfileBean userProfileBean;

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }

    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOfferSelection
    private List<EntertainmentOfferSelection> usersEntertainmentOfferSelections = Arrays.asList(
            new EntertainmentOfferSelection("singer", "Musiker/Sänger"),
            new EntertainmentOfferSelection("illusionist", "Illusionist"),
            new EntertainmentOfferSelection("host", "Entertainer/Moderator"),
            new EntertainmentOfferSelection("painter", "Schnellzeichner"),
            new EntertainmentOfferSelection("actor", "Schauspieler"));

    public List<EntertainmentOfferSelection> getUsersEntertainmentOfferSelections() {
        return usersEntertainmentOfferSelections;
    }

    public void setUsersEntertainmentOfferSelections(List<EntertainmentOfferSelection> usersEntertainmentOfferSelections) {
        this.usersEntertainmentOfferSelections = usersEntertainmentOfferSelections;
    }

    public String getVisibility(EntertainmentOfferSelection offer) {
        return offer.isEnabled() ? "" : "invisible"; // returns either nothing or the CSS-Class "invisible"
    }


    public void save() {
        entertainmentServicesDAO.update(getProvider(), usersEntertainmentOfferSelections);
    }
}
