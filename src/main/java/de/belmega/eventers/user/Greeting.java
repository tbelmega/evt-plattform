package de.belmega.eventers.user;

public enum Greeting {

    MR("Herr", "Sehr geehrter Herr"), MRS("Frau", "Sehr geehrte Frau"), COMPANY("Firma", "Sehr geehrte Damen und Herren");

    private final String name;
    private final String greeting;

    private Greeting(String name, String greeting) {
        this.name = name;
        this.greeting = greeting;
    }


    public String getName() {
        return name;
    }

    public String getGreeting() {
        return greeting;
    }
}
