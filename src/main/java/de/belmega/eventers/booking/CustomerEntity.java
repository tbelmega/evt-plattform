package de.belmega.eventers.booking;

import de.belmega.eventers.user.Greeting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class CustomerEntity {

    @Id
    @GeneratedValue
    long id;
    private String company;
    private Greeting greeting;
    private String firstName;
    private String lastName;
    private String emailadress;
    private String mobile;
    private String hotelAdress;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setGreeting(Greeting greeting) {
        this.greeting = greeting;
    }

    public Greeting getGreeting() {
        return greeting;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setHotelAdress(String hotelAdress) {
        this.hotelAdress = hotelAdress;
    }

    public String getHotelAdress() {
        return hotelAdress;
    }
}
