package de.belmega.eventers.user.services;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class MassageServicesBean {

    private List<String> focusByUser = new ArrayList<>();
    private List<String> allAvailableFocuses = Arrays.asList("Ganzkörpermassage", "Medizinische Massage", "Wellnessmassage");
    private List<String> selectionIfMassageTableIsAvailable = Arrays.asList("Nein");
    private List<String> selectionIfMassageChairIsAvailable = Arrays.asList("Ja");

    public void setFocusByUser(List<String> focusByUser) {
        this.focusByUser = focusByUser;
    }

    public List<String> getFocusByUser() {
        return focusByUser;
    }

    public void setAllAvailableFocuses(List<String> allAvailableFocuses) {
        this.allAvailableFocuses = allAvailableFocuses;
    }

    public List<String> getAllAvailableFocuses() {
        return allAvailableFocuses;
    }

    public void setSelectionIfMassageTableIsAvailable(List<String> selectionIfMassageTableIsAvailable) {
        this.selectionIfMassageTableIsAvailable = selectionIfMassageTableIsAvailable;
    }

    public List<String> getSelectionIfMassageTableIsAvailable() {
        return selectionIfMassageTableIsAvailable;
    }

    public void setSelectionIfMassageChairIsAvailable(List<String> selectionIfMassageChairIsAvailable) {
        this.selectionIfMassageChairIsAvailable = selectionIfMassageChairIsAvailable;
    }

    public List<String> getSelectionIfMassageChairIsAvailable() {
        return selectionIfMassageChairIsAvailable;
    }
}
