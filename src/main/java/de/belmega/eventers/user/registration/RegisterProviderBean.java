package de.belmega.eventers.user.registration;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserID;
import de.belmega.eventers.user.ProviderService;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;


@Named
@RequestScoped
public class RegisterProviderBean implements Serializable {

    @Inject
    ProviderService providerService;

    @Inject
    @ConfigurationValue("urls.pages.registered")
    String registeredSite;

    private ProviderUserEntity provider;

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
            mailadressAlreadyInUse.printStackTrace();
            return "";
        }
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }
}
