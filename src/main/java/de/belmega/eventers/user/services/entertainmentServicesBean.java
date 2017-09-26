package de.belmega.eventers.user.services;

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
public class entertainmentServicesBean {

    private List<String> offeredEntertainmentsByUser = new ArrayList<>();

    // Since the h:datatable wants to access the .name and .id and .enabled of each list element, it needs to be a full object instead of as string.
    // Hence the class EntertainmentOffer
    private List<EntertainmentOffer> allAvailableEntertainmentOffers = Arrays.asList(
            new EntertainmentOffer("singer", "Musiker/Sänger"),
            new EntertainmentOffer("illusionist", "Illusionist"),
            new EntertainmentOffer("host", "Entertainer/Moderator"),
            new EntertainmentOffer("painter", "Schnellzeichner"),
            new EntertainmentOffer("actor", "Schauspieler"));

    public void setOfferedEntertainmentsByUser(List<String> offeredEntertainmentsByUser) {
        this.offeredEntertainmentsByUser = offeredEntertainmentsByUser;
    }

    public List<String> getOfferedEntertainmentsByUser() {
        return offeredEntertainmentsByUser;
    }

    public void setAllAvailableEntertainmentOffers(List<EntertainmentOffer> allAvailableEntertainmentOffers) {
        this.allAvailableEntertainmentOffers = allAvailableEntertainmentOffers;
    }

    public List<EntertainmentOffer> getAllAvailableEntertainmentOffers() {
        return allAvailableEntertainmentOffers;
    }


}
