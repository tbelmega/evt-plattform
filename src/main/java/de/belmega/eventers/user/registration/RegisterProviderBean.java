package de.belmega.eventers.user.registration;

import de.belmega.eventers.services.categories.CategoryDAO;
import de.belmega.eventers.services.categories.CategoryEntity;
import de.belmega.eventers.services.categories.ServiceDAO;
import de.belmega.eventers.services.categories.ServiceEntity;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserID;
import de.belmega.eventers.user.ProviderService;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


@Named
@RequestScoped
public class RegisterProviderBean implements Serializable {

    @Inject
    ProviderService providerService;

    @Inject
    CategoryDAO categoryDAO;

    @Inject
    ServiceDAO serviceDAO;

    @Inject
    @ConfigurationValue("urls.pages.registered")
    String registeredSite;

    private ProviderUserEntity provider;
    private List<CategoryEntity> allAvailableCategories;
    private String category;
    private Object service;

    @PostConstruct
    public void init() {
        this.provider = new ProviderUserEntity();
    }

    public String clear() {
        this.provider = new ProviderUserEntity();
        return "";
    }

    public String register() {

        try {
            UserID serviceProviderID = providerService.registerNewProvider(provider);
            return registeredSite + "?faces-redirect=true";
        } catch (MailadressAlreadyInUse mailadressAlreadyInUse) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Diese Mailadresse wird bereits genutzt.", null));
            return "";
        }
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }

    public List<CategoryEntity> getAllAvailableCategories() {
        return categoryDAO.findAll();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public List<ServiceEntity> getServicesForCategory() {
        // If no category selected yet, don't load services
        if (StringUtils.isEmpty(category)) return Collections.emptyList();
        else return serviceDAO.findServicesByCategory(category);
    }

    public void setService(Object service) {
        this.service = service;
    }

    public Object getService() {
        return service;
    }

    // This is called when user selects a category
    public void categorySelected() {
        RequestContext.getCurrentInstance().update("selectService");
    }
}
