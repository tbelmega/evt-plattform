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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
        List<CategoryEntity> all = categoryDAO.findAll();
        if (all.isEmpty()) generateProvidedServices();

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
        CategoryEntity massage = new CategoryEntity(ServiceCategoryId.MASSAGE.name(), "Massage");
        categoryDAO.persist(massage);
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

        createServiceEntity("body", massage, "Ganzkörpermassage");
        createServiceEntity("medical", massage, "Medizinische Massage");
        createServiceEntity("wellness-massage", massage, "Wellnessmassage");

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
        createServiceEntity("author", kultur, "Schriftsteller oder Vorleser");

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

    private void createServiceEntity(String serviceId, CategoryEntity categoryEntity, String serviceName) {
        ServiceEntity massage = new ServiceEntity(serviceId, categoryEntity, serviceName);
        serviceDAO.persist(massage);
    }

    private void generateTestData() throws MailadressAlreadyInUse {

        try {
            registerUser("1", "Belmega", "Thiemo", "foo", "foo",
                    Arrays.asList(ServiceCategoryId.values()));
            registerUser("2", "Wilfling", "Jochen", "info@coorgeist.de", "eventers789!",
                    Arrays.asList(ServiceCategoryId.values()));

            registerUser("3", "3", "3", "3", "3",
                    Arrays.asList(ServiceCategoryId.CULINARIC));

            registerUser("4", "4", "4", "4", "4",
                    Arrays.asList(ServiceCategoryId.CULTURE));

            registerUser("5", "5", "5", "5", "5",
                    Arrays.asList(ServiceCategoryId.ENTERTAINMENT));

            registerUser("6", "6", "6", "6", "6",
                    Arrays.asList(ServiceCategoryId.MASSAGE));

            registerUser("7", "7", "7", "7", "7",
                    Arrays.asList(ServiceCategoryId.SPORTS));

            registerUser("8", "8", "8", "8", "8",
                    Arrays.asList(ServiceCategoryId.TRANSPORTATION));

            registerUser("9", "9", "9", "9", "9",
                    Arrays.asList(ServiceCategoryId.WELLNESS));

        } catch (MailadressAlreadyInUse e) {
            LOG.warn("Test users already existing.");
        }

        LOG.warn("Test data injection complete.");
    }

    private void registerUser(String id, String surname, String firstname, String emailadress, String password, List<ServiceCategoryId> categories) throws MailadressAlreadyInUse {
        ProviderUserEntity user = new ProviderUserEntity();
        user.setLastname(surname);
        user.setFirstname(firstname);
        user.setEmailadress(emailadress);
        user.setPasswordPlainText(password);

        HashSet<String> categoryIds = new HashSet<>();
        for (ServiceCategoryId categoryId: categories)
            categoryIds.add(categoryId.name());
        user.setCategoryIds(categoryIds);

        providerService.registerNewProvider(user);
    }


}
