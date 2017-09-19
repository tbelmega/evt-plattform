package de.belmega.eventers.jsf;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import static de.belmega.eventers.filter.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@RequestScoped
public class RegistrationSuccesfulBean {

    //public static final String HOSTNAME = "http://the-eventers.de";
    public static final String HOSTNAME = "localhost:8080";

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
        this.testlink = HOSTNAME + "/profil-fitness.xhtml?id=" + serviceProviderId;
    }

    public String getTestlink() {
        return testlink;
    }
}
