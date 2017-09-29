package de.belmega.eventers.services.common;

public class OfferSelection {
    private String categoryName;

    private String serviceId;
    private String serviceName;

    private boolean enabled;
    private String description;


    public OfferSelection(String serviceId, String serviceName, String categoryName) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.categoryName = categoryName;
    }

    public OfferSelection(String categoryName, String serviceId, String serviceName, boolean enabled, String description) {
        this.categoryName = categoryName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.enabled = enabled;
        this.description = description;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
