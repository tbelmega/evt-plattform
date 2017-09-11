package de.belmega.eventers.dto;

public class ServiceProviderUserTO {

    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
    private String profession;
    private ServiceProviderID id;
    private String passwordPlainText;


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

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }

    public void setId(ServiceProviderID id) {
        this.id = id;
    }

    public ServiceProviderID getId() {
        return id;
    }

    public String getPasswordPlainText() {
        return passwordPlainText;
    }

    public void setPasswordPlainText(String passwordPlainText) {
        this.passwordPlainText = passwordPlainText;
    }
}

