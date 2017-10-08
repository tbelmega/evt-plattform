package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthService;
import de.belmega.eventers.mail.EmailSessionBean;
import de.belmega.eventers.user.registration.exceptions.MailadressAlreadyInUse;
import org.apache.commons.lang.RandomStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ProviderService {

    @Inject
    AuthService authService;

    @Inject
    EmailSessionBean emailSessionBean;

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

    public void resetPassword(String mailadress) {
        Optional<ProviderUserEntity> user = userDAO.findByEmailAdress(mailadress);

        if (user.isPresent()) sendNewPassword(user.get());
    }

    private void sendNewPassword(ProviderUserEntity provider) {

        String newPassword = RandomStringUtils.randomAlphanumeric(20);
        provider.setEncryptedPassword(authService.encrypt(newPassword.toCharArray(), provider.getSalt()));

        userDAO.update(provider);

        emailSessionBean.sendEmail(provider.getEmailadress(), "Neues Passwort",
                "Ihr Passwort wurde zurückgesetzt. Das neue Passwort lautet: " + newPassword);
    }
}
