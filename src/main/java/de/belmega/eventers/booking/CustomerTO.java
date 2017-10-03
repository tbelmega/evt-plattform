package de.belmega.eventers.booking;

public class CustomerTO {

    private Long id;
    private String greeting;
    private String firstname;
    private String lastname;
    private String emailadress;
    private String mobile;
    private String company;
    private String hotelAdress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setHotelAdress(String hotelAdress) {
        this.hotelAdress = hotelAdress;
    }

    public String getHotelAdress() {
        return hotelAdress;
    }
}
