package de.belmega.eventers.user;

import de.belmega.eventers.user.UserID;

import java.time.LocalDate;

public class ProviderUserTO {

    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
    private String profession;
    private UserID id;
    private String passwordPlainText;
    private String street;
    private String zipcode;
    private String city;
    private LocalDate dateOfBirth;
    private String phoneBusiness;
    private String mobile;
    private String degree;
    private String experience;
    private String contactable;
    private String mobility;
    private String hourlyRate;
    private String services;
    private String locations;
    private String equipments;

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

    public UserID getId() {
        return id;
    }

    public void setId(UserID id) {
        this.id = id;
    }

    public String getPasswordPlainText() {
        return passwordPlainText;
    }

    public void setPasswordPlainText(String passwordPlainText) {
        this.passwordPlainText = passwordPlainText;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneBusiness() {
        return phoneBusiness;
    }

    public void setPhoneBusiness(String phoneBusiness) {
        this.phoneBusiness = phoneBusiness;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getContactable() {
        return contactable;
    }

    public void setContactable(String contactable) {
        this.contactable = contactable;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }
}

