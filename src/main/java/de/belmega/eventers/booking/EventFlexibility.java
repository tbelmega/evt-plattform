package de.belmega.eventers.booking;

public enum EventFlexibility {

    NONE("nicht flexibel", 0),
    MIN_30("30 Minuten", 30),
    MIN_60("1 Stunde", 60),
    MIN_90("1,5 Stunden", 90),
    MIN_120("2 Stunden", 120),
    MIN_150("2,5 Stunden", 150),
    MIN_180("3 Stunden", 180),
    MIN_240("4 Stunden", 240),
    MIN_300("5 Stunden", 300);


    private final String text;
    private final int minutes;

    EventFlexibility(String text, int minutes) {
        this.text = text;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getText() {
        return text;
    }
}
