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
    private List<String> allAvailableEntertainmentOffers = Arrays.asList("Musiker/Sänger", "Illusionist",
            "Entertainer/Moderator", "Schnellzeichner", "Schauspieler");

    public void setOfferedEntertainmentsByUser(List<String> offeredEntertainmentsByUser) {
        this.offeredEntertainmentsByUser = offeredEntertainmentsByUser;
    }

    public List<String> getOfferedEntertainmentsByUser() {
        return offeredEntertainmentsByUser;
    }

    public void setAllAvailableEntertainmentOffers(List<String> allAvailableEntertainmentOffers) {
        this.allAvailableEntertainmentOffers = allAvailableEntertainmentOffers;
    }

    public List<String> getAllAvailableEntertainmentOffers() {
        return allAvailableEntertainmentOffers;
    }


}
