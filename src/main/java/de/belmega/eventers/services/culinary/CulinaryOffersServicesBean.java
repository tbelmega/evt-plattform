package de.belmega.eventers.services.culinary;

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
public class CulinaryOffersServicesBean {


    @Inject
    private CulinaryServicesDAO culinaryServicesDAO;

    @Inject
    private UserProfileBean userProfileBean;

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }


    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOffer
    private List<CulinaryOfferSelection> usersCulinaryOfferSelections = Arrays.asList(
            new CulinaryOfferSelection("wine-tasting", "Weinverkostung"),
            new CulinaryOfferSelection("whiskey-tasting", "Whiskey-Tasting"),
            new CulinaryOfferSelection("cocktail-course", "Cocktail-Kurs"),
            new CulinaryOfferSelection("restaurant", "Restaurant"),
            new CulinaryOfferSelection("chocolate-tasting", "Schokoladenverkostung " +
                    "(idealerweise gilt das Angebot mit Wein- und Spirituosenverkostung)"),
            new CulinaryOfferSelection("cheese-tasting", "Käseverkostung"));

    public void setUsersCulinaryOfferSelections(List<CulinaryOfferSelection> usersCulinaryOfferSelections) {
        this.usersCulinaryOfferSelections = usersCulinaryOfferSelections;
    }

    public List<CulinaryOfferSelection> getUsersCulinaryOfferSelections() {
        return usersCulinaryOfferSelections;
    }


    public void save() {
        culinaryServicesDAO.update(getProvider(), usersCulinaryOfferSelections);
    }

    // if offer is not enabled, hide the text area
    public String getVisibility(CulinaryOfferSelection culinaryOfferSelection) {
        return culinaryOfferSelection.isEnabled() ? "" : "invisible"; // returns either nothing or the CSS-Class "invisible"
    }
}
