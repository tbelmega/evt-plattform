package de.belmega.eventers;

import de.belmega.eventers.services.CategoryDAO;
import de.belmega.eventers.services.ServiceDAO;
import de.belmega.eventers.user.*;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import de.belmega.eventers.services.CategoryEntity;
import de.belmega.eventers.services.ServiceEntity;
import org.jboss.logging.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class TestDataGenerator {

    @Inject
    private ProviderService providerService;

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    @ConfigurationValue("test-environment")
    Boolean testEnvironment;

    private static final Logger LOG = Logger.getLogger(TestDataGenerator.class);



    public void setupTestData(@Observes @Initialized(ApplicationScoped.class) Object init) throws MailadressAlreadyInUse {
        generateProvidedServices();

        if (testEnvironment) {
            LOG.warn("Test environment detected. Generating test data.");
            generateTestData();
        } else {
            LOG.warn("Productive environment detected. Not generating test data.");
        }

    }

    /**
     * Creates the service categories and services from the specification and writes them into the database.
     */
    private void generateProvidedServices() {
        CategoryEntity wellness = new CategoryEntity("Wellness");
        categoryDAO.persist(wellness);

        createServiceEntity(wellness, "Massage");
        createServiceEntity(wellness, "Kosmetik");
        createServiceEntity(wellness, "Nageldesign");
        createServiceEntity(wellness, "Make-up Artist");
        createServiceEntity(wellness, "Friseur");

        // TODO: Add the other categories and services from Erfassungsformular_Dienstleister_neu.xlsx Kategorien_Dienstl.
        // Bewegung, Entertainment...
        CategoryEntity fitness = new CategoryEntity("Bewegung");
        categoryDAO.persist(fitness);

    }

    private void createServiceEntity(CategoryEntity wellness, String serviceName) {
        ServiceEntity massage = new ServiceEntity(wellness, serviceName);
        serviceDAO.persist(massage);
    }

    private void generateTestData() throws MailadressAlreadyInUse {
        registerUser("1", "Belmega", "Thiemo", "foo", "foo");
        registerUser("2", "Wilfling", "Jochen", "info@coorgeist.de", "eventers789!");

        LOG.warn("Test data injection complete.");
    }

    private void registerUser(String id, String surname, String firstname, String emailadress, String password) throws MailadressAlreadyInUse {
        ProviderUserEntity user = new ProviderUserEntity();
        user.setLastname(surname);
        user.setFirstname(firstname);
        user.setEmailadress(emailadress);
        user.setPasswordPlainText(password);

        providerService.registerNewProvider(user);
    }


}
