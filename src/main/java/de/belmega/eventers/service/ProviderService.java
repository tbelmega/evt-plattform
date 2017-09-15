package de.belmega.eventers.service;

import de.belmega.eventers.dto.UserID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.persistence.dao.ProviderDAO;
import de.belmega.eventers.persistence.entities.ServiceProviderUserEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderDAO providerDAO;

    public UserID registerNewProvider(ServiceProviderUserTO provider) {
        ServiceProviderUserEntity entity = new ServiceProviderUserEntity();
        entity.setId(UserID.generateId());
        entity.setFirstname(provider.getFirstname());
        entity.setLastname(provider.getLastname());
        entity.setEmailadress(provider.getEmailadress());
        entity.setGreeting(provider.getGreeting());
        entity.setProfession(provider.getProfession());

        providerDAO.persist(entity);
        
        return entity.getId();
    }

    public ServiceProviderUserTO findProvider(UserID serviceProviderID) {

        System.out.println(serviceProviderID);

        ServiceProviderUserEntity entity = providerDAO.findById(serviceProviderID);

        System.out.println(entity);

        ServiceProviderUserTO to = new ServiceProviderUserTO();
        to.setId(serviceProviderID);
        to.setEmailadress(entity.getEmailadress());
        to.setFirstname(entity.getFirstname());
        to.setLastname(entity.getLastname());
        to.setGreeting(entity.getGreeting());
        to.setProfession(entity.getProfession());

        return to;
    }
}
