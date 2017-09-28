package de.belmega.eventers.user;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ProviderUserEntity {


    @Id
    @EmbeddedId
    private UserID id;
    private byte[] salt;
    private byte[] encryptedPassword;

    @Transient // transient = will not be saved in the database
    private String passwordPlainText;

    private String firstname;
    private String lastname;
    private String emailadress;
    private String greeting;
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

    @Transient // TODO add relational tables later
    private List<String> services;
    @Transient // TODO add relational tables later
    private List<String> locations;
    @Transient // TODO add relational tables later
    private List<String> equipments;


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

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }
}
