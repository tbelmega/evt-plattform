package de.belmega.eventers.persistence.entities;

import de.belmega.eventers.dto.UserID;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScheduleEventEntity {

    @Id
    private String id;
    private String title;
    private Date startDate;
    private Date endDate;

    @ManyToOne(optional=false)
    @JoinColumn
    private ServiceProviderUserEntity user;

    public ScheduleEventEntity(String id, String title, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ServiceProviderUserEntity getUser() {
        return user;
    }

    public void setUser(ServiceProviderUserEntity user) {
        this.user = user;
    }
}
