package de.belmega.eventers.user.registration;

import de.belmega.eventers.user.UserID;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import static de.belmega.eventers.auth.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@RequestScoped
public class RegistrationSuccesfulBean {

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    @Inject
    @ConfigurationValue("urls.sites.profile")
    String profileSite;

    private UserID serviceProviderId;
    private String testlink;

    public void setServiceProviderId(UserID serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public UserID getServiceProviderId() {
        return serviceProviderId;
    }

    public void generateLink() {
        ((HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(true)).setAttribute(ATTRIBUTE_USER_ID, serviceProviderId);
        this.testlink = hostname + profileSite + "?id=" + serviceProviderId;
    }

    public String getTestlink() {
        return testlink;
    }
}
