package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthService;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ProviderService {

    @Inject
    AuthService authService;

    @Inject
    UserDAO userDAO;

    public UserID registerNewProvider(ProviderUserEntity provider) throws MailadressAlreadyInUse {

        Optional<ProviderUserEntity> byEmailAdress = userDAO.findByEmailAdress(provider.getEmailadress());
        if (byEmailAdress.isPresent()) throw new MailadressAlreadyInUse();

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

    public Optional<ProviderUserEntity> findById(UserID userId) {
        return userDAO.findById(userId);
    }
}
