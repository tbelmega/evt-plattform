package de.belmega.eventers.auth;

import de.belmega.eventers.user.*;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Optional;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    @ConfigurationValue("urls.pages.profile")
    String profilePage;

    @Inject
    @ConfigurationValue("urls.pages.login")
    String loginPage;

    @Inject
    @ConfigurationValue("urls.pages.registration")
    String registerPage;

    public static final String FACES_REDIRECT_TRUE_PARAMETER = "?faces-redirect=true";

    @Inject
    AuthService authService;

    @Inject
    ProviderService providerService;


    private ProviderUserEntity sessionUser;

    /** The values of the <input> elements on index.xhtml page are bound to these fields: */
    private String emailAdress;
    private String password;

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Validate the entered user credentials.
     * Otherwise, stay on the current page.
     */
    public String validateUsernamePassword() {
        Optional<ProviderUserEntity> user = authService.validate(emailAdress, password);
        if (user.isPresent()) {
            this.sessionUser = user.get();
            System.out.println("Login succesful, welcome " + user.get().getFirstname());

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute(AuthFilter.ATTRIBUTE_USER_ID, this.sessionUser.getId());

            return profilePage + FACES_REDIRECT_TRUE_PARAMETER;
        } else {
            getHttpSession().invalidate();
            return loginPage + FACES_REDIRECT_TRUE_PARAMETER;
        }

    }

    /**
     * Get the current HttpSession object from the FacesContext (JSF framework).
     */
    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(true);
    }

    /**
     * Invalidate the user session and redirect to index page.
     */
    public String logout() {
        this.sessionUser = null;
        getHttpSession().invalidate();
        return loginPage + FACES_REDIRECT_TRUE_PARAMETER;
    }

    public String registration() {
        return registerPage + "?faces-redirect=true";
    }

    public ProviderUserEntity getSessionUser() {
        if (sessionUser == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            UserID userId = (UserID) session.getAttribute(AuthFilter.ATTRIBUTE_USER_ID);
            System.out.println("Looking for user with ID " + userId.getId());
            Optional<ProviderUserEntity> providerUserEntity = providerService.findById(userId);
            this.sessionUser = providerUserEntity.get();
        }
        return sessionUser;
    }
}
