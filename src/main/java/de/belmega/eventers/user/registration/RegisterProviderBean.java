package de.belmega.eventers.user.registration;

import de.belmega.eventers.user.UserID;
import de.belmega.eventers.user.ProviderUserTO;
import de.belmega.eventers.user.ProviderService;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class RegisterProviderBean {

    @Inject
    ProviderService providerService;

    @Inject
    @ConfigurationValue("urls.pages.registered")
    String registeredSite;



    private ProviderUserTO provider;

    @PostConstruct
    public void init() {
        this.provider = new ProviderUserTO();
    }

    public String clear() {
        this.provider = new ProviderUserTO();
        return "";
    }

    public String register() {
        UserID serviceProviderID = providerService.registerNewProvider(provider);

        return registeredSite + "?faces-redirect=true&id=" + serviceProviderID.getId();
    }

    public void setProvider(ProviderUserTO provider) {
        this.provider = provider;
    }

    public ProviderUserTO getProvider() {
        return provider;
    }
}
