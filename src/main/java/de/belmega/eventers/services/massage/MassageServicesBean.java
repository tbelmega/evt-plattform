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

    private MassageServicesEntity data;


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

    public MassageServicesEntity getData() {
        if (data == null)
            data = massageServicesDAO.loadMassageServicesEntityForUser(getProvider());
        return data;
    }

    public void setData(MassageServicesEntity data) {
        this.data = data;
    }

    public String save() {
        selectionServicesDAO.update(getProvider(), usersMassageOfferSelections);
        massageServicesDAO.update(getProvider(), data);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
