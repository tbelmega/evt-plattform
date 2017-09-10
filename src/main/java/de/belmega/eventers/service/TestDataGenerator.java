package de.belmega.eventers.service;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class TestDataGenerator {

    @PersistenceContext
    EntityManager em;

    private static final Logger LOG = Logger.getLogger(TestDataGenerator.class);



    public void setupTestData(@Observes @Initialized(ApplicationScoped.class) Object init) {


        LOG.warn("Test data injection complete.");
    }


}
