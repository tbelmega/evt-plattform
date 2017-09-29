package de.belmega.eventers.services.transport;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.services.common.OfferSelection;
import de.belmega.eventers.services.common.SelectionServicesDAO;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.List;


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

    private List<OfferSelection> usersTransportOfferSelections;

    public void setUsersTransportOfferSelections(List<OfferSelection> usersTransportOfferSelections) {
        this.usersTransportOfferSelections = usersTransportOfferSelections;
    }

    public List<OfferSelection> getUsersTransportOfferSelections() {

        if (usersTransportOfferSelections == null) usersTransportOfferSelections =
                selectionServicesDAO.findSelectionsForUser(getProvider(), ServiceCategoryId.TRANSPORTATION);

        return usersTransportOfferSelections;
    }

    public String getVisibility(OfferSelection offer) {
        return offer.isEnabled() ? "" : "invisible"; // returns either nothing or the CSS-Class "invisible"
    }


    public void save() {
        selectionServicesDAO.update(getProvider(), usersTransportOfferSelections);
    }
}
