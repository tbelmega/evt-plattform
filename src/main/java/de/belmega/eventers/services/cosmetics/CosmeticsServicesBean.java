package de.belmega.eventers.services.cosmetics;

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
    CosmeticsServicesDAO cosmeticsServicesDAO;

    private List<String> selectedCosmeticOffersByUser = new ArrayList<>();
    private List<String> allAvailableCosmeticOffers = Arrays.asList("Kosmetik allgemein", "Pediküre", "Maniküre", "Typ-& Stilberatung",
            "Make up Artist", "Nageldesign", "Hair Stylist/Friseur");

    public void setSelectedCosmeticOffersByUser(List<String> selectedCosmeticOffersByUser) {
        this.selectedCosmeticOffersByUser = selectedCosmeticOffersByUser;
    }

    public List<String> getSelectedCosmeticOffersByUser() {
        return selectedCosmeticOffersByUser;
    }

    public void setAllAvailableCosmeticOffers(List<String> allAvailableCosmeticOffers) {
        this.allAvailableCosmeticOffers = allAvailableCosmeticOffers;
    }

    public List<String> getAllAvailableCosmeticOffers() {
        return allAvailableCosmeticOffers;
    }

    public String save() {
        if (selectedCosmeticOffersByUser == null) selectedCosmeticOffersByUser = Collections.emptyList();

        cosmeticsServicesDAO.update(getProvider(), selectedCosmeticOffersByUser);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
