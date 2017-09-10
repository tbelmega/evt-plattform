package de.belmega.pardernote.jsf;

import de.belmega.pardernote.dto.ServiceProviderUserTO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@RequestScoped
public class RegisterProviderBean {

    public static final String NOTELIST_PAGE = "notelist?faces-redirect=true";
    public static final String INDEX_PAGE = "index?faces-redirect=true";

    private ServiceProviderUserTO provider;

    @PostConstruct
    public void init() {
        this.provider = new ServiceProviderUserTO();
    }

    public String clear() {
        this.provider = new ServiceProviderUserTO();
        return "";
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }


    public String register() {
        return "";
    }

    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }
}
