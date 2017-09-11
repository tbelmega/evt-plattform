package de.belmega.eventers.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public class RegistrationSuccesfulBean {

    public static final String HOSTNAME = "localhost:8080";
    //public static final String HOSTNAME = "https://the-eventers.herokuapp.com";


    private String serviceProviderId;
    private String testlink;

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void generateTestLink() {
        this.testlink = HOSTNAME + "/profile.xhtml?id=" + serviceProviderId;
    }

    public String getTestlink() {
        return testlink;
    }
}
