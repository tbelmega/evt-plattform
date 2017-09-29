package de.belmega.eventers.services.culinary;

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
public class CulinaryOffersServicesBean {


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
    private List<OfferSelection> usersCulinaryOfferSelections = Arrays.asList(
            new OfferSelection("wine-tasting", "Weinverkostung"),
            new OfferSelection("whiskey-tasting", "Whiskey-Tasting"),
            new OfferSelection("cocktail-course", "Cocktail-Kurs"),
            new OfferSelection("restaurant", "Restaurant"),
            new OfferSelection("chocolate-tasting", "Schokoladenverkostung " +
                    "(idealerweise gilt das Angebot mit Wein- und Spirituosenverkostung)"),
            new OfferSelection("cheese-tasting", "Käseverkostung"));

    public void setUsersCulinaryOfferSelections(List<OfferSelection> usersCulinaryOfferSelections) {
        this.usersCulinaryOfferSelections = usersCulinaryOfferSelections;
    }

    public List<OfferSelection> getUsersCulinaryOfferSelections() {
        return usersCulinaryOfferSelections;
    }

    public void save() {
        selectionServicesDAO.update(getProvider(), usersCulinaryOfferSelections);
    }

    // if offer is not enabled, hide the text area
    public String getVisibility(OfferSelection culinaryOfferSelection) {
        return culinaryOfferSelection.isEnabled() ? "" : "invisible"; // returns either nothing or the CSS-Class "invisible"
    }
}
