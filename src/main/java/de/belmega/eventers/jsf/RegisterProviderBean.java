package de.belmega.eventers.jsf;

import de.belmega.eventers.dto.ServiceProviderID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.service.ProviderService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@RequestScoped
public class RegisterProviderBean {

    @Inject
    ProviderService providerService;


    private ServiceProviderUserTO provider;

    @PostConstruct
    public void init() {
        this.provider = new ServiceProviderUserTO();
    }

    public String clear() {
        this.provider = new ServiceProviderUserTO();
        return "";
    }



    public String register() {
        ServiceProviderID serviceProviderID = providerService.registerNewProvider(provider);

        return "registered.xhtml?faces-redirect=true&id=" + serviceProviderID.getId();
    }

    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }
}
