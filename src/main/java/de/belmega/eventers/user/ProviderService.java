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

        provider.setId(UserID.generateId());

        byte[] salt = authService.generateSalt();
        provider.setSalt(salt);

        provider.setEncryptedPassword(authService.encrypt(provider.getPasswordPlainText().toCharArray(), salt));

        userDAO.persist(provider);

        return provider.getId();
    }

    public Optional<ProviderUserEntity> findById(UserID userId) {
        return userDAO.findById(userId);
    }
}
