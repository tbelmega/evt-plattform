package de.belmega.eventers;

import de.belmega.eventers.scheduling.ScheduleEventDAO;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.services.categories.*;
import de.belmega.eventers.services.common.OfferSelection;
import de.belmega.eventers.services.common.SelectionServicesDAO;
import de.belmega.eventers.user.*;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.io.IOUtils;

@ApplicationScoped
public class TestDataGenerator {

    @Inject
    private ProviderService providerService;

    @Inject
    private ServiceDAO serviceDAO;

    @Inject
    private SelectionServicesDAO selectionServicesDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private ScheduleEventDAO scheduleEventDAO;

    @Inject
    @ConfigurationValue("test-environment")
    Boolean testEnvironment;

    private static final Logger LOG = Logger.getLogger(TestDataGenerator.class);
    private Map<String, Integer> pricing;


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

        int hourlyRate = getPricing().get(serviceId);

        ServiceEntity entity = new ServiceEntity(serviceId, categoryEntity, serviceName, hourlyRate);
        serviceDAO.persist(entity);
    }

    private void generateTestData() throws MailadressAlreadyInUse {

        try {
            ProviderUserEntity tbelmega = registerUser("1", "Belmega", "Thiemo", "t.belmega@gmx.de", "foo",
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

            enableService(tbelmega);
            registerAvailability(tbelmega);

        } catch (MailadressAlreadyInUse e) {
            LOG.warn("Test users already existing.");
        }

        LOG.warn("Test data injection complete.");
    }

    private ProviderUserEntity registerUser(String id, String surname, String firstname, String emailadress, String password, List<ServiceCategoryId> categories) throws MailadressAlreadyInUse {
        ProviderUserEntity user = new ProviderUserEntity();
        user.setLastname(surname);
        user.setFirstname(firstname);
        user.setEmailadress(emailadress);
        user.setPasswordPlainText(password);

        HashSet<String> categoryIds = new HashSet<>();
        for (ServiceCategoryId categoryId : categories)
            categoryIds.add(categoryId.name());
        user.setCategoryIds(categoryIds);

        providerService.registerNewProvider(user);

        return user;
    }

    private void registerAvailability(ProviderUserEntity user) {
        // Add an all-day availability for today
        ScheduleEventEntity scheduleEventEntity = new ScheduleEventEntity();
        scheduleEventEntity.setId(UUID.randomUUID().toString());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 1);

        scheduleEventEntity.setStartDate(cal.getTime());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        scheduleEventEntity.setEndDate(cal.getTime());

        scheduleEventEntity.setUser(user);

        scheduleEventDAO.persist(scheduleEventEntity);
    }

    private void enableService(ProviderUserEntity user) {
        // Enable "Culture" services for this user
        List<OfferSelection> selectionsForUser = selectionServicesDAO.findSelectionsForUser(user, ServiceCategoryId.CULTURE);
        for (OfferSelection selection: selectionsForUser)
            selection.setEnabled(true);
    }


    private Map<String, Integer> getPricing() {
        if (pricing == null) {
            try {
                pricing = loadPricing();
            } catch (IOException e) {
                throw new RuntimeException("Reading of pricing file failed.");
            }
        }
        return pricing;
    }

    private Map<String, Integer> loadPricing() throws IOException {
        Map<String, Integer> pricing = new HashMap<>();

        InputStream in = TestDataGenerator.class.getClassLoader().getResourceAsStream("pricing.csv");
        if (in == null) throw new FileNotFoundException();
        String data = IOUtils.toString(in, StandardCharsets.UTF_8.name());
        List<String> rows = Arrays.asList(data.split("\n"));
        for (int i = 1; i < rows.size(); i++) {
            String row = rows.get(i);

            if (StringUtils.isNotBlank(row)) {
                String[] elements = row.split(";");
                pricing.put(elements[1].trim(), getValue(elements[2]));
            }
        }
        in.close();
        return pricing;
    }

    private int getValue(String element) {
        try {
            return Integer.parseInt(element.trim());
        } catch (NumberFormatException e) {
            System.err.println(e);
            Logger.getLogger(TestDataGenerator.class).error(e);
            Logger.getLogger(TestDataGenerator.class).error(element);
            return 0;
        }
    }

    public void setPricing(Map<String, Integer> pricing) {
        this.pricing = pricing;
    }
}
