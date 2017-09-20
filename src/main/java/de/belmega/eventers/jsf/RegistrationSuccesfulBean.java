package de.belmega.eventers.jsf;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import static de.belmega.eventers.filter.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@RequestScoped
public class RegistrationSuccesfulBean {

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    //public static final String hostname = "localhost:8080";
    @Inject
    @ConfigurationValue("urls.sites.profil-fitness")
    String profilFitnessSite;

    private String serviceProviderId;
    private String testlink;

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void generateTestLink() {
        ((HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(true)).setAttribute(ATTRIBUTE_USER_ID, serviceProviderId);
        this.testlink = hostname + profilFitnessSite + "?id=" + serviceProviderId;
        System.out.println(this.testlink);
    }

    public String getTestlink() {
        return testlink;
    }
}
