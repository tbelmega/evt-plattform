package de.belmega.eventers;

import de.belmega.eventers.services.categories.*;
import de.belmega.eventers.user.*;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import org.jboss.logging.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

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
        CategoryEntity wellness = new CategoryEntity(ServiceCategoryId.WELLNESS.name(), "Wellness");
        categoryDAO.persist(wellness);
        CategoryEntity bewegung = new CategoryEntity(ServiceCategoryId.SPORTS.name(), "Bewegung");
        categoryDAO.persist(bewegung);
        CategoryEntity entertainment = new CategoryEntity(ServiceCategoryId.ENTERTAINMENT.name(), "Entertainment");
        categoryDAO.persist(entertainment);
        CategoryEntity kultur = new CategoryEntity(ServiceCategoryId.CULTURE.name(), "Kultur");
        categoryDAO.persist(kultur);
        CategoryEntity kulinarisches = new CategoryEntity(ServiceCategoryId.CULINARIC.name(), "Kulinarisches");
        categoryDAO.persist(kulinarisches);
        CategoryEntity transport = new CategoryEntity(ServiceCategoryId.TRANSPORTATION.name(), "Transport");
        categoryDAO.persist(transport);

        createServiceEntity(wellness, "Massage");
        createServiceEntity(wellness, "Kosmetik");
        createServiceEntity(wellness, "Nageldesign");
        createServiceEntity(wellness, "Make-up Artist");
        createServiceEntity(wellness, "Friseur");

        createServiceEntity(bewegung, "Fitnesstrainer / Personal Coach");
        createServiceEntity(bewegung, "Wanderführer");
        createServiceEntity(bewegung, "Tanzlehrer");
        createServiceEntity(bewegung, "Yogalehrer");
        createServiceEntity(bewegung, "Tai Chi/Qigong-Lehrer");

        createServiceEntity(entertainment, "Musiker/Sänger");
        createServiceEntity(entertainment, "Illusionist");
        createServiceEntity(entertainment, "Entertainer/Moderator");
        createServiceEntity(entertainment, "Kabarattist");
        createServiceEntity(entertainment, "Schnellzeichner");
        createServiceEntity(entertainment, "Schauspieler");

        createServiceEntity(kultur, "Stadtführer");
        createServiceEntity(kultur, "Kunstführer");
        createServiceEntity(kultur, "Schriftsteller oder Vorleser");

        createServiceEntity(kulinarisches, "Sommelier");
        createServiceEntity(kulinarisches, "Chocolatier");
        createServiceEntity(kulinarisches, "Destilllateur");
        createServiceEntity(kulinarisches, "Käsesommelier");
        createServiceEntity(kulinarisches, "Barkeeper");
        createServiceEntity(kulinarisches, "Restaurant");

        createServiceEntity(transport, "Taxiunternehmen");
        createServiceEntity(transport, "Limosinenservice");
        createServiceEntity(transport, "Velotaxi-Unternehmen");
        createServiceEntity(transport, "Fahrradverleih");

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
