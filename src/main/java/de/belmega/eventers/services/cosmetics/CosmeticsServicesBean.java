package de.belmega.eventers.services.cosmetics;

import de.belmega.eventers.services.categories.ServiceCategoryId;
import de.belmega.eventers.services.common.OfferSelection;
import de.belmega.eventers.services.common.SelectionServicesDAO;
import de.belmega.eventers.services.massage.MassageServicesDAO;
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
public class CosmeticsServicesBean {


    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    private SelectionServicesDAO selectionServicesDAO;


    private List<OfferSelection> selectedCosmeticOffersByUser;

    public void setSelectedCosmeticOffersByUser(List<OfferSelection> selectedCosmeticOffersByUser) {
        this.selectedCosmeticOffersByUser = selectedCosmeticOffersByUser;
    }

    public List<OfferSelection> getSelectedCosmeticOffersByUser() {

        if (selectedCosmeticOffersByUser == null) selectedCosmeticOffersByUser =
                selectionServicesDAO.findSelectionsForUser(getProvider(), ServiceCategoryId.WELLNESS);

        return selectedCosmeticOffersByUser;
    }

    public String save() {
        selectionServicesDAO.update(getProvider(), selectedCosmeticOffersByUser);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
