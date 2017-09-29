package de.belmega.eventers.services.common;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.*;

@Entity
public class SelectionEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ProviderUserEntity provider;

    private String offerId;

    private boolean enabled;

    private String description;

    private String categoryName;

    public SelectionEntity(ProviderUserEntity provider, String offerId, boolean enabled, String description, String categoryName) {
        this.provider = provider;
        this.offerId = offerId;
        this.enabled = enabled;
        this.description = description;
    }

    public SelectionEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
