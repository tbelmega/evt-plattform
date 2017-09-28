package de.belmega.eventers.services;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by majab on 26.09.2017.
 */
@ManagedBean
@SessionScoped
public class CulturalOffersServicesBean {

    private List<String> selectedCulturalOfferByUser = new ArrayList<>();

    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOffer
    private List<CulturalOffers> allAvailableCulturalOffers = Arrays.asList(
            new CulturalOffers("city-guide", "Stadtführung"),
            new CulturalOffers("museum-guide", "Museumsführung/Kulturführung"),
            new CulturalOffers("reader", "Vorleser/Schriftsteller"));


    public void setSelectedCulturalOfferByUser(List<String> selectedCulturalOfferByUser) {
        this.selectedCulturalOfferByUser = selectedCulturalOfferByUser;
    }

    public List<String> getSelectedCulturalOfferByUser() {
        return selectedCulturalOfferByUser;
    }

    public void setAllAvailableCulturalOffers(List<CulturalOffers> allAvailableCulturalOffers) {
        this.allAvailableCulturalOffers = allAvailableCulturalOffers;
    }

    public List<CulturalOffers> getAllAvailableCulturalOffers() {
        return allAvailableCulturalOffers;
    }


}
