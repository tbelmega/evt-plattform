package de.belmega.eventers.dto;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserID implements Serializable {

    private String id;

    public UserID(String id) {
        this.id = id;
    }

    public UserID() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static UserID generateId() {
        UUID uuid = UUID.randomUUID();
        return new UserID(uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserID)) return false;

        UserID that = (UserID) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserID{" +
                "id='" + id + '\'' +
                '}';
    }
}
