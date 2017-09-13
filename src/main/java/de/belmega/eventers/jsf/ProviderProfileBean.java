package de.belmega.eventers.jsf;

import de.belmega.eventers.dto.ServiceProviderID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.service.ProviderService;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

import static de.belmega.eventers.filter.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@SessionScoped
public class ProviderProfileBean implements Serializable {

    @Inject
    ProviderService providerService;

    private ServiceProviderUserTO provider;

    private String serviceProviderId;

    public void loadProfile() throws AuthException {

        if (FacesContext.getCurrentInstance().isPostback()) return;

        String idFromSession = (String) getHttpSession().getAttribute(ATTRIBUTE_USER_ID);
        if(!serviceProviderId.equals(idFromSession)){
            System.out.println(idFromSession);
            throw new AuthException("Zugriff auf dieses Nutzerprofil ist nicht gestattet.");
        }

        ServiceProviderID id = new ServiceProviderID(serviceProviderId);
        this.provider = providerService.findProvider(id);

    }

    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }

    public void setServiceProviderId(String serviceProviderId) {
        System.out.println("ID set from URL: " + serviceProviderId);
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public Object save() {
        return null;
    }
}
