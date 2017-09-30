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

        createServiceEntity("massage", wellness, "Massage");
        createServiceEntity("cosmetics", wellness, "Kosmetik");
        createServiceEntity("nails", wellness, "Nageldesign");
        createServiceEntity("make-up", wellness, "Make-up Artist");
        createServiceEntity("barber", wellness, "Friseur");

        createServiceEntity("personal-coach", bewegung, "Fitnesstrainer / Personal Coach");
        createServiceEntity("hiking-guide", bewegung, "Wanderführer");
        createServiceEntity("dancing", bewegung, "Tanzlehrer");
        createServiceEntity("yoga", bewegung, "Yogalehrer");
        createServiceEntity("tai-chi", bewegung, "Tai Chi/Qigong-Lehrer");

        createServiceEntity("musician", entertainment, "Musiker/Sänger");
        createServiceEntity("illusionist", entertainment, "Illusionist");
        createServiceEntity("entertainer", entertainment, "Entertainer/Moderator");
        createServiceEntity("cabaret", entertainment, "Kabarattist");
        createServiceEntity("drawer", entertainment, "Schnellzeichner");
        createServiceEntity("actor", entertainment, "Schauspieler");

        createServiceEntity("city-guide", kultur, "Stadtführer");
        createServiceEntity("art-guide", kultur, "Kunstführer");
        createServiceEntity("", kultur, "Schriftsteller oder Vorleser");

        createServiceEntity("sommelier", kulinarisches, "Sommelier");
        createServiceEntity("chocolatier", kulinarisches, "Chocolatier");
        createServiceEntity("destillateuer", kulinarisches, "Destilllateur");
        createServiceEntity("cheese", kulinarisches, "Käsesommelier");
        createServiceEntity("barkeeper", kulinarisches, "Barkeeper");
        createServiceEntity("restaurant", kulinarisches, "Restaurant");

        createServiceEntity("taxi", transport, "Taxiunternehmen");
        createServiceEntity("limosine", transport, "Limosinenservice");
        createServiceEntity("velotaxi", transport, "Velotaxi-Unternehmen");
        createServiceEntity("bike-rental", transport, "Fahrradverleih");

    }

    private void createServiceEntity(String serviceId, CategoryEntity wellness, String serviceName) {
        ServiceEntity massage = new ServiceEntity(serviceId, wellness, serviceName);
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
