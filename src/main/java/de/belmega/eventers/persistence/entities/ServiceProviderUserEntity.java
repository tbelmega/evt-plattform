package de.belmega.eventers.persistence.entities;

import de.belmega.eventers.dto.ServiceProviderID;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServiceProviderUserEntity {


    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
    private String profession;

    @Id
    @EmbeddedId
    private ServiceProviderID id;

    public ServiceProviderID getId() {
        return id;
    }

    public void setId(ServiceProviderID id) {
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

    @Override
    public String toString() {
        return "ServiceProviderUserEntity{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailadress='" + emailadress + '\'' +
                ", greeting='" + greeting + '\'' +
                ", profession='" + profession + '\'' +
                ", id=" + id +
                '}';
    }
}
