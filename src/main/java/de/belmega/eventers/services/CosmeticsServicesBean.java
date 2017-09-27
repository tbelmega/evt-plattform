package de.belmega.eventers.services;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by majab on 26.09.2017.
 */
@Named
@SessionScoped
public class CosmeticsServicesBean {
    private List<String> selectedCosmeticOffersByUser = new ArrayList<>();
    private List<String> allAvailableCosmeticOffers = Arrays.asList("Kosmtik allgemein", "Pediküre", "Maniküre", "Typ-& Stilberatung",
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
}
