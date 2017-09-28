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
public class CulinaryOffersServicesBean {

    private List<String> selectedCulinaryOfferByUser = new ArrayList<>();

    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOffer
    private List<CulinaryOffers> allAvailableCulinaryOffers = Arrays.asList(
            new CulinaryOffers("wine-tasting", "Weinverkostung"),
            new CulinaryOffers("whiskey-tasting", "Whiskey-Tasting"),
            new CulinaryOffers("cocktail-course", "Cocktail-Kurs"),
            new CulinaryOffers("restaurant", "Restaurant"),
            new CulinaryOffers("chocolate-tasting", "Schokoladenverkostungverkostung " +
                    "(idealerweise gilt das Angebot mit Wein-, und Spirituosenverkostung"),
            new CulinaryOffers("cheese-tasting", "Käseverkostung"));


    public void setSelectedCulinaryOfferByUser(List<String> selectedCulinaryOfferByUser) {
        this.selectedCulinaryOfferByUser = selectedCulinaryOfferByUser;
    }

    public List<String> getSelectedCulinaryOfferByUser() {
        return selectedCulinaryOfferByUser;
    }

    public void setAllAvailableCulinaryOffers(List<CulinaryOffers> allAvailableCulinaryOffers) {
        this.allAvailableCulinaryOffers = allAvailableCulinaryOffers;
    }

    public List<CulinaryOffers> getAllAvailableCulinaryOffers() {
        return allAvailableCulinaryOffers;
    }


}
