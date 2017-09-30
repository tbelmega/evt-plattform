package de.belmega.eventers.services.massage;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by majab on 29.09.2017.
 */
@Entity
public class MassageServicesEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne // every user has only one FitnessServicesEntity associated with it.
    ProviderUserEntity provider;

    private Boolean massageTable;

    private Boolean chair;

    public MassageServicesEntity(ProviderUserEntity provider, Boolean massageTable, Boolean chair) {
        this.provider = provider;
        this.massageTable = massageTable;
        this.chair = chair;
    }

    public MassageServicesEntity() {
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

    public Boolean getMassageTable() {
        return massageTable;
    }

    public void setMassageTable(Boolean massageTable) {
        this.massageTable = massageTable;
    }

    public Boolean getChair() {
        return chair;
    }

    public void setChair(Boolean chair) {
        this.chair = chair;
    }
}
