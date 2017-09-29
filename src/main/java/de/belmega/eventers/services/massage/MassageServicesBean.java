package de.belmega.eventers.services.massage;

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
public class MassageServicesBean {


    @Inject
    private UserProfileBean userProfileBean;

    @Inject
    MassageServicesDAO massageServicesDAO;

    private List<String> allAvailableFocuses = Arrays.asList("Ganzkörpermassage", "Medizinische Massage", "Wellnessmassage");

    // User selection lists:
    private List<String> focusByUser = new ArrayList<>();
    private boolean selectionIfMassageTableIsAvailable;
    private boolean selectionIfMassageChairIsAvailable;

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

    public UserProfileBean getUserProfileBean() {
        return userProfileBean;
    }

    public void setUserProfileBean(UserProfileBean userProfileBean) {
        this.userProfileBean = userProfileBean;
    }

    public MassageServicesDAO getMassageServicesDAO() {
        return massageServicesDAO;
    }

    public void setMassageServicesDAO(MassageServicesDAO massageServicesDAO) {
        this.massageServicesDAO = massageServicesDAO;
    }

    public boolean isSelectionIfMassageTableIsAvailable() {
        return selectionIfMassageTableIsAvailable;
    }

    public void setSelectionIfMassageTableIsAvailable(boolean selectionIfMassageTableIsAvailable) {
        this.selectionIfMassageTableIsAvailable = selectionIfMassageTableIsAvailable;
    }

    public boolean isSelectionIfMassageChairIsAvailable() {
        return selectionIfMassageChairIsAvailable;
    }

    public void setSelectionIfMassageChairIsAvailable(boolean selectionIfMassageChairIsAvailable) {
        this.selectionIfMassageChairIsAvailable = selectionIfMassageChairIsAvailable;
    }

    public String save() {
        // if user didn't select anything (= focusByUser is null), set empty list to prevent NullPointer
        if (focusByUser == null) focusByUser = Collections.emptyList();

        System.out.println(focusByUser + " " + selectionIfMassageTableIsAvailable + " " + selectionIfMassageChairIsAvailable);
        massageServicesDAO.update(getProvider(), focusByUser, selectionIfMassageTableIsAvailable, selectionIfMassageChairIsAvailable);
        return "";
    }

    // Get the currently logged in user from the userProfileBean
    private ProviderUserEntity getProvider() {
        return userProfileBean.getProvider();
    }
}
