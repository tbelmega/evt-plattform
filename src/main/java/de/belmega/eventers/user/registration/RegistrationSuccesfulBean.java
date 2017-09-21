package de.belmega.eventers.user.registration;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.user.UserID;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

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
public class RegistrationSuccesfulBean implements Serializable {

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    @Inject
    @ConfigurationValue("urls.pages.profile")
    String profileSite;

    private String testlink;

    public void generateLink() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        UserID serviceProviderId = (UserID) session.getAttribute(AuthFilter.ATTRIBUTE_USER_ID);

        this.testlink = hostname + profileSite + "?id=" + serviceProviderId.getId();
    }

    public String getTestlink() {
        if (testlink == null) generateLink();
        System.out.println(testlink);
        return testlink;
    }
}
