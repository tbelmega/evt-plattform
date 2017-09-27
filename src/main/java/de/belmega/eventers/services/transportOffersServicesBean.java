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
public class transportOffersServicesBean {

    private List<String> selectedTransportOfferByUser = new ArrayList<>();

    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOffer
    private List<TransportOffers> allAvailableTransportOffers = Arrays.asList(
            new TransportOffers("taxi-service", "Taxi-Service"),
            new TransportOffers("limousine-service", "Limosinen-Service"),
            new TransportOffers("velo-taxi", "Velotaxi-Service"),
            new TransportOffers("bicycle-rentals", "Fahrradverleih"));


    public void setSelectedTransportOfferByUser(List<String> selectedTransportOfferByUser) {
        this.selectedTransportOfferByUser = selectedTransportOfferByUser;
    }

    public List<String> getSelectedTransportOfferByUser() {
        return selectedTransportOfferByUser;
    }

    public void setAllAvailableTransportOffers(List<TransportOffers> allAvailableCulturalOffers) {
        this.allAvailableTransportOffers = allAvailableCulturalOffers;
    }

    public List<TransportOffers> getAllAvailableTransportOffers() {
        return allAvailableTransportOffers;
    }


}
