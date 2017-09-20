package de.belmega.eventers.auth;

import de.belmega.eventers.user.UserDAO;
import de.belmega.eventers.user.ProviderUserEntity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserDAO dao;

    /**
     * Check if the entered credentials are valid.
     *
     * @param enteredEmailAdress
     * @param enteredPassword
     * @return true, if the entered credentials are valid.
     */
    public Optional<ProviderUserEntity> validate(String enteredEmailAdress, String enteredPassword) {
        Optional<ProviderUserEntity> userEntity = dao.findByEmailAdress(enteredEmailAdress);

        if (userEntity.isPresent() && checkPassword(enteredPassword, userEntity.get())) {
            return userEntity;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Check if the entered password matches the UserEntity from database.
     */
    private boolean checkPassword(String enteredPasswordPlainText, ProviderUserEntity userEntity) {
        byte[] storedPassword = userEntity.getEncryptedPassword();
        byte[] enteredPasswordEncrypted = encrypt(enteredPasswordPlainText.toCharArray(), userEntity.getSalt());

        return Arrays.equals(storedPassword, enteredPasswordEncrypted);
    }

    /**
     * Salt the given password and encrypt with PBKDF2WithHmacSHA512 algorithm.
     */
    public byte[] encrypt(char[] passwordPlainText, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(passwordPlainText, salt, 1000, 512);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[64];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

}
