package de.belmega.eventers.jsf;

import de.belmega.eventers.dto.ServiceProviderID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.service.ProviderService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@RequestScoped
public class ProviderProfileBean {

    @Inject
    ProviderService providerService;

    private ServiceProviderUserTO provider;
    private String serviceProviderId;


    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void loadProfile() {
        this.provider = providerService.findProvider(new ServiceProviderID(serviceProviderId));
    }
}
