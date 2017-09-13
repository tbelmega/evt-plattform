package de.belmega.eventers.jsf;

import de.belmega.eventers.dto.ServiceProviderID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.service.ProviderService;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

import static de.belmega.eventers.filter.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@SessionScoped
public class ProviderProfileBean implements Serializable {

    @Inject
    ProviderService providerService;

    private ServiceProviderUserTO provider;

    @ManagedProperty(value = "#{request.getParameter('id')}")
    private String serviceProviderId;


    public void loadProfile() {
        ServiceProviderID id = new ServiceProviderID(serviceProviderId);


        String attribute = (String) getHttpSession().getAttribute(ATTRIBUTE_USER_ID);
        System.out.println(id);
        System.out.println(attribute); // TODO
        this.provider = providerService.findProvider(id);

    }

    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

}
