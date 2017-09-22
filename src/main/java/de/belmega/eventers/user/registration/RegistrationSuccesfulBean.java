package de.belmega.eventers.user.registration;

import de.belmega.eventers.user.UserID;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;


@Named
@RequestScoped
public class RegistrationSuccesfulBean implements Serializable {

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    @Inject
    @ConfigurationValue("urls.pages.profile")
    String profileSite;

    public String getTestlink() {
        return  hostname + profileSite;
    }
}
