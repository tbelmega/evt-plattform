package de.belmega.eventers.booking;

public enum EventFlexibility {

    NONE("nicht flexibel"),
    MIN_30("30 Minuten"), MIN_60("1 Stunde"), MIN_90("1,5 Stunden"), MIN_120("2 Stunden"), MIN_150("2,5 Stunden"),
    MIN_180("3 Stunden"), MIN_240("4 Stunden"), MIN_300("5 Stunden");


    private final String text;

    EventFlexibility(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
