package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.mail.EmailSessionBean;
import de.belmega.eventers.services.categories.ServiceCategoryId;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ManagedBean
@RequestScoped
public class PasswordRecoveryBean {

    @Inject
    ProviderService providerService;


    private String mailadress = "";

    public void setMailadress(String mailadress) {
        this.mailadress = mailadress;
    }

    public String getMailadress() {
        return mailadress;
    }

    public void submit() {

        providerService.resetPassword(mailadress);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Neues Passwort versandt", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
