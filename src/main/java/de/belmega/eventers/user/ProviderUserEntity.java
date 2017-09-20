package de.belmega.eventers.user;

import de.belmega.eventers.user.UserID;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProviderUserEntity {


    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
    private String profession;

    @Id
    @EmbeddedId
    private UserID id;
    private byte[] salt;
    private byte[] encryptedPassword;


    public UserID getId() {
        return id;
    }

    public void setId(UserID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "ProviderUserEntity{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailadress='" + emailadress + '\'' +
                ", greeting='" + greeting + '\'' +
                ", profession='" + profession + '\'' +
                ", id=" + id +
                '}';
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
