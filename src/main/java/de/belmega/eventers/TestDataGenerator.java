package de.belmega.eventers;

import de.belmega.eventers.user.*;
import org.jboss.logging.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

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
    ProviderService providerService;

    @Inject
    @ConfigurationValue("test-environment")
    Boolean testEnvironment;

    private static final Logger LOG = Logger.getLogger(TestDataGenerator.class);



    public void setupTestData(@Observes @Initialized(ApplicationScoped.class) Object init) {
        if (testEnvironment) {
            LOG.warn("Test environment detected. Generating test data.");
            generateTestData();
        } else {
            LOG.warn("Productive environment detected. Not generating test data.");
        }

    }

    private void generateTestData() {
        registerUser("1", "Belmega", "Thiemo", "foo", "foo");
        registerUser("2", "Wilfling", "Jochen", "info@coorgeist.de", "eventers789!");

        LOG.warn("Test data injection complete.");
    }

    private void registerUser(String id, String surname, String firstname, String emailadress, String password) {
        ProviderUserTO user = new ProviderUserTO();
        user.setId(new UserID(id));
        user.setLastname(surname);
        user.setFirstname(firstname);
        user.setEmailadress(emailadress);
        user.setPasswordPlainText(password);

        providerService.registerNewProvider(user);
    }


}
