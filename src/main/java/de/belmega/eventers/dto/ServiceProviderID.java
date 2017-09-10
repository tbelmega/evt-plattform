package de.belmega.eventers.dto;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ServiceProviderID implements Serializable {

    private String id;

    public ServiceProviderID(String id) {
        this.id = id;
    }

    public ServiceProviderID() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ServiceProviderID generateId() {
        UUID uuid = UUID.randomUUID();
        return new ServiceProviderID(uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceProviderID)) return false;

        ServiceProviderID that = (ServiceProviderID) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
