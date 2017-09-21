package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ProviderService {

    @Inject
    AuthService authService;

    @Inject
    UserDAO userDAO;

    public UserID registerNewProvider(ProviderUserTO provider) {
        ProviderUserEntity entity = new ProviderUserEntity();
        entity.setId(UserID.generateId());

        byte[] salt = authService.generateSalt();
        entity.setSalt(salt);

        entity.setEncryptedPassword(authService.encrypt(provider.getPasswordPlainText().toCharArray(), salt));

        entity.setFirstname(provider.getFirstname());
        entity.setLastname(provider.getLastname());
        entity.setEmailadress(provider.getEmailadress());
        entity.setGreeting(provider.getGreeting());
        entity.setProfession(provider.getProfession());

        userDAO.persist(entity);

        return entity.getId();
    }

    public Optional<ProviderUserTO> findProvider(UserID serviceProviderID) {

        System.out.println(serviceProviderID);

        Optional<ProviderUserEntity> optEntity = userDAO.findById(serviceProviderID);

        if (!optEntity.isPresent()) return Optional.empty();


        ProviderUserEntity entity = optEntity.get();
        System.out.println(entity);

        ProviderUserTO to = new ProviderUserTO();
        to.setId(serviceProviderID);
        to.setEmailadress(entity.getEmailadress());
        to.setFirstname(entity.getFirstname());
        to.setLastname(entity.getLastname());
        to.setGreeting(entity.getGreeting());
        to.setProfession(entity.getProfession());

        return Optional.of(to);
    }

    public void update(ProviderUserTO provider) {
        ProviderUserEntity optEntity = userDAO.findById(provider.getId()).get();

        optEntity.setFirstname(provider.getFirstname());
        optEntity.setLastname(provider.getLastname());

    }

    public Optional<ProviderUserEntity> findById(UserID userId) {
        return userDAO.findById(userId);
    }
}
