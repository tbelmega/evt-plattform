package de.belmega.eventers.services.entertainment;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EntertainmentSelectionEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne // every user has only one EntertainmentSelectionEntity associated with it.
    private ProviderUserEntity provider;

    private String offerId;

    private boolean enabled;

    private String description;

    public EntertainmentSelectionEntity(ProviderUserEntity provider, String offerId, boolean enabled, String description) {
        this.provider = provider;
        this.offerId = offerId;
        this.enabled = enabled;
        this.description = description;
    }

    public EntertainmentSelectionEntity() {
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
}
