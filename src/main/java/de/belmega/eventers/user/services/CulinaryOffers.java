package de.belmega.eventers.user.services;

/**
 * Created by majab on 27.09.2017.
 */
public class CulinaryOffers {
    private String name;
    private boolean enabled;
    private String id;

    public CulinaryOffers(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
