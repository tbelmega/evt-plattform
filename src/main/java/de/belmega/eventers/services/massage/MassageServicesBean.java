package de.belmega.eventers.services.massage;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@RequestScoped
public class MassageServicesBean {


    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    MassageServicesDAO massageServicesDAO;

    private List<String> allAvailableFocuses = Arrays.asList("Ganzkörpermassage", "Medizinische Massage", "Wellnessmassage");

    // User selection lists:
    private List<String> focusByUser = new ArrayList<>();
    private List<String> selectionIfMassageTableIsAvailable = new ArrayList<>();
    private List<String> selectionIfMassageChairIsAvailable = new ArrayList<>();

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

    public String save() {
        System.out.println(focusByUser.size() + " " + selectionIfMassageTableIsAvailable.size() + " " + selectionIfMassageChairIsAvailable);
        massageServicesDAO.update(getProvider(), focusByUser, selectionIfMassageTableIsAvailable, selectionIfMassageChairIsAvailable);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
