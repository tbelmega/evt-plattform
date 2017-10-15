package de.belmega.eventers.paypal;

public enum PaymentIntent {

    AUTHORIZE("authorize");

    private final String id;

    PaymentIntent(String intent) {
        this.id = intent;
    }

    public String getId() {
        return id;
    }
}
