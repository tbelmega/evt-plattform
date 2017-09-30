package de.belmega.eventers.services.culinary;

import de.belmega.eventers.services.categories.ServiceCategoryId;
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


    private List<OfferSelection> usersCulinaryOfferSelections;

    public void setUsersCulinaryOfferSelections(List<OfferSelection> usersCulinaryOfferSelections) {
        this.usersCulinaryOfferSelections = usersCulinaryOfferSelections;
    }

    public List<OfferSelection> getUsersCulinaryOfferSelections() {

        if (usersCulinaryOfferSelections == null) usersCulinaryOfferSelections =
                selectionServicesDAO.findSelectionsForUser(getProvider(), ServiceCategoryId.CULINARIC);

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
