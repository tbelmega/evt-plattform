package de.belmega.eventers.services.cosmetics;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CosmeticsServicesEntity {

    @Id
    @GeneratedValue
    long id;

    @OneToOne
    ProviderUserEntity provider;

    @ElementCollection(targetClass = String.class)
    List<String> selectedCosmeticOffersByUser;

    public CosmeticsServicesEntity(ProviderUserEntity provider, List<String> selectedCosmeticOffersByUser) {
        this.provider = provider;
        this.selectedCosmeticOffersByUser = selectedCosmeticOffersByUser;
    }

    public CosmeticsServicesEntity() {
    }

    public ProviderUserEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public List<String> getSelectedCosmeticOffersByUser() {
        return selectedCosmeticOffersByUser;
    }

    public void setSelectedCosmeticOffersByUser(List<String> selectedCosmeticOffersByUser) {
        this.selectedCosmeticOffersByUser = selectedCosmeticOffersByUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
