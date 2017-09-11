package de.belmega.eventers.service;

import de.belmega.eventers.dto.ServiceProviderID;
import de.belmega.eventers.persistence.dao.ProviderDAO;
import de.belmega.eventers.persistence.entities.ServiceProviderUserEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class TestDataGenerator {

    @PersistenceContext
    EntityManager em;

    @Inject
    ProviderDAO dao;

    private static final Logger LOG = Logger.getLogger(TestDataGenerator.class);



    public void setupTestData(@Observes @Initialized(ApplicationScoped.class) Object init) {
        ServiceProviderUserEntity entity = new ServiceProviderUserEntity();
        entity.setId(new ServiceProviderID("1"));
        entity.setLastname("Belmega");
        entity.setFirstname("Thiemo");
        entity.setEmailadress("thiemo.belmega@gmail.com");

        dao.persist(entity);

        System.out.println(entity);

        LOG.warn("Test data injection complete.");
    }


}
