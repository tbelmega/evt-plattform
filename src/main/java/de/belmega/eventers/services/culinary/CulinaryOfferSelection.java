package de.belmega.eventers.services.culinary;


/**
 * This class combines the information of a CulinarySelectionEntity and the related CulinaryOfferEntity for display purposes.
 */
public class CulinaryOfferSelection {

    private String id;

    private String name;

    private boolean enabled;

    private String description;

    public CulinaryOfferSelection(String id, String name) {
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
