package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.auth.LoginBean;
import de.belmega.eventers.scheduling.EventProperties;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.scheduling.ScheduleEventService;
import de.belmega.eventers.services.categories.ServiceCategoryId;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@SessionScoped
public class UserProfileBean implements Serializable {

    @Inject
    ProviderService providerService;

    private ProviderUserEntity provider;
    private String currentPage;

    public void save() {
        providerService.userDAO.update(provider);
    }



    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public ProviderUserEntity getProvider() {
        if (this.provider == null) this.provider = loadProfile();

        return this.provider;
    }

    private ProviderUserEntity loadProfile() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UserID userId = (UserID) session.getAttribute(AuthFilter.ATTRIBUTE_USER_ID);
        Optional<ProviderUserEntity> providerUserEntity = providerService.findById(userId);

        if (!providerUserEntity.isPresent()) throw new RuntimeException("Trying to load a user that does not exist.");

        ProviderUserEntity userEntity = providerUserEntity.get();


        return userEntity;
    }

    public boolean isInRoleFitness() {
        String fitness = ServiceCategoryId.SPORTS.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleCulinaric() {
        String fitness = ServiceCategoryId.CULINARIC.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleCulture() {
        String fitness = ServiceCategoryId.CULTURE.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleWellness() {
        String fitness = ServiceCategoryId.WELLNESS.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleMassage() {
        String fitness = ServiceCategoryId.MASSAGE.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleEntertainment() {
        String fitness = ServiceCategoryId.ENTERTAINMENT.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleTransportation() {
        String fitness = ServiceCategoryId.TRANSPORTATION.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    private String getPage(String currentPage) {
        switch (currentPage) {
            case "profile": return "profile.xhtml";
            case "services": return "services.xhtml";
            case "calendar": return "calendar.xhtml";
            case "assignments": return "assignments.xhtml";
            default: System.out.println(currentPage); return "";
        }
    }

    public void navigate() throws IOException {
        String url = getPage(this.currentPage);
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public String getCurrentPage() {
        return currentPage;
    }
}
