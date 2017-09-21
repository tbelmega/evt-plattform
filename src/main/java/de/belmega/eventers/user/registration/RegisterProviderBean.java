package de.belmega.eventers.user.registration;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.auth.LoginBean;
import de.belmega.eventers.user.UserID;
import de.belmega.eventers.user.ProviderUserTO;
import de.belmega.eventers.user.ProviderService;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import java.io.Serializable;

import static de.belmega.eventers.auth.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@SessionScoped
public class RegisterProviderBean implements Serializable {

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

        System.out.println("Registered provider with ID " + serviceProviderID);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute(AuthFilter.ATTRIBUTE_USER_ID, serviceProviderID);

        return registeredSite + "?faces-redirect=true";
    }

    public void setProvider(ProviderUserTO provider) {
        this.provider = provider;
    }

    public ProviderUserTO getProvider() {
        return provider;
    }
}
