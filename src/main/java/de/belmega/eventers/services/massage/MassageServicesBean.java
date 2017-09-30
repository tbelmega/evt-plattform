package de.belmega.eventers.services.massage;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.services.common.OfferSelection;
import de.belmega.eventers.services.common.SelectionServicesDAO;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ManagedBean
@RequestScoped
public class MassageServicesBean {


    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    MassageServicesDAO massageServicesDAO;


    @Inject
    private SelectionServicesDAO selectionServicesDAO;

    private List<OfferSelection> usersMassageOfferSelections;

    private Boolean selectionIfMassageTableIsAvailable;
    private Boolean selectionIfMassageChairIsAvailable;

    public List<OfferSelection> getUsersMassageOfferSelections() {
        if (usersMassageOfferSelections == null) usersMassageOfferSelections =
                selectionServicesDAO.findSelectionsForUser(getProvider(), ServiceCategoryId.MASSAGE);

        return usersMassageOfferSelections;
    }

    public void setUsersMassageOfferSelections(List<OfferSelection> usersMassageOfferSelections) {
        this.usersMassageOfferSelections = usersMassageOfferSelections;
    }

    public UserProfileBean getUserProfileBean() {
        return userProfileBean;
    }

    public void setUserProfileBean(UserProfileBean userProfileBean) {
        this.userProfileBean = userProfileBean;
    }

    public boolean isSelectionIfMassageTableIsAvailable() {
        return selectionIfMassageTableIsAvailable;
    }

    public void setSelectionIfMassageTableIsAvailable(boolean selectionIfMassageTableIsAvailable) {
        this.selectionIfMassageTableIsAvailable = selectionIfMassageTableIsAvailable;
    }

    public boolean isSelectionIfMassageChairIsAvailable() {
        return selectionIfMassageChairIsAvailable;
    }

    public void setSelectionIfMassageChairIsAvailable(boolean selectionIfMassageChairIsAvailable) {
        this.selectionIfMassageChairIsAvailable = selectionIfMassageChairIsAvailable;
    }

    public String save() {
        selectionServicesDAO.update(getProvider(), usersMassageOfferSelections);
        massageServicesDAO.update(getProvider(), selectionIfMassageTableIsAvailable, selectionIfMassageChairIsAvailable);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
