package de.belmega.eventers.dto;

import java.time.LocalDate;

public class ServiceProviderUserTO {

    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
    private String profession;
    private UserID id;
    private String passwordPlainText;
    private Object street;
    private Object zipcode;
    private Object city;
    private LocalDate dateOfBirth;
    private Object phoneBusiness;
    private Object mobile;
    private Object degree;
    private Object experience;
    private Object contactable;
    private Object mobility;
    private Object hourlyRate;
    private Object services;
    private Object locations;
    private Object equipments;


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

    public void setId(UserID id) {
        this.id = id;
    }

    public UserID getId() {
        return id;
    }

    public String getPasswordPlainText() {
        return passwordPlainText;
    }

    public void setPasswordPlainText(String passwordPlainText) {
        this.passwordPlainText = passwordPlainText;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getStreet() {
        return street;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCity() {
        return city;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setPhoneBusiness(Object phoneBusiness) {
        this.phoneBusiness = phoneBusiness;
    }

    public Object getPhoneBusiness() {
        return phoneBusiness;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setDegree(Object degree) {
        this.degree = degree;
    }

    public Object getDegree() {
        return degree;
    }

    public void setExperience(Object experience) {
        this.experience = experience;
    }

    public Object getExperience() {
        return experience;
    }

    public void setContactable(Object contactable) {
        this.contactable = contactable;
    }

    public Object getContactable() {
        return contactable;
    }

    public void setMobility(Object mobility) {
        this.mobility = mobility;
    }

    public Object getMobility() {
        return mobility;
    }

    public void setHourlyRate(Object hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Object getHourlyRate() {
        return hourlyRate;
    }

    public void setServices(Object services) {
        this.services = services;
    }

    public Object getServices() {
        return services;
    }

    public void setLocations(Object locations) {
        this.locations = locations;
    }

    public Object getLocations() {
        return locations;
    }

    public void setEquipments(Object equipments) {
        this.equipments = equipments;
    }

    public Object getEquipments() {
        return equipments;
    }
}

