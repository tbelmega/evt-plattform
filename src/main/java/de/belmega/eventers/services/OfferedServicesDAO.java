package de.belmega.eventers.services;

import de.belmega.eventers.user.ProviderUserEntity;

import java.util.*;

public class OfferedServicesDAO {

    // TODO: move to database. hard-coded values for testing only
    public List<String> findServicesForProvider(ProviderUserEntity provider) {
        return Arrays.asList(
                "Training als Personal Coach in einem Sportstudio",
                "Personal Coach Laufen / Joggen im Park",
                "Personal Coach Nordic Walking im Park",
                "Personal Coach in einer Kletterhalle / Klettergarten",
                "Begleitung als Trainer / Schiedsrichter / Spieler bei Indoor Fußball");
    }

    // TODO: move to database. hard-coded values for testing only
    public List<String> findServices(ServiceType fitness) {
        return Arrays.asList(
                "Training als Personal Coach in einem Sportstudio",
                "Personal Coach Laufen / Joggen im Park",
                "Personal Coach Nordic Walking im Park",
                "Personal Coach in einer Kletterhalle / Klettergarten",
                "Yoga / Tai Chi",
                "Pilates",
                "Begleitung als Trainer / Schiedsrichter / Spieler bei Indoor Fußball");
    }

    // TODO: move to database. hard-coded values for testing only
    public List<String> findLocationsByUser(ProviderUserEntity provider) {
        return Collections.emptyList();
    }

    // TODO: move to database. hard-coded values for testing only
    public List<String> findLocations(ServiceType fitness) {
        return Arrays.asList(
                "Indoor", "Outdoor", "Gym", "Hotel");
    }

    // TODO: move to database. hard-coded values for testing only
    public List<String> findEquipmentByUser(ProviderUserEntity provider) {
        return Collections.emptyList();
    }

    // TODO: move to database. hard-coded values for testing only
    public List<String> findEquipment(ServiceType fitness) {
        return Arrays.asList(
                "Nordic Walking Stöcke", "Isomatten", "Federballsets", "Walking Hanteln");
    }
}
