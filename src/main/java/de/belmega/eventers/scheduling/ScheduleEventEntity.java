package de.belmega.eventers.scheduling;

import de.belmega.eventers.user.ProviderUserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScheduleEventEntity {

    public static final String COLUMN_USER_ID = "user_id";

    @Id
    private String id;
    private String title;
    private Date startDate;
    private Date endDate;

    @ManyToOne(optional=false)
    @JoinColumn(name = COLUMN_USER_ID)
    private ProviderUserEntity user;

    public ScheduleEventEntity(String id, String title, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ScheduleEventEntity() {
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

    public ProviderUserEntity getUser() {
        return user;
    }

    public void setUser(ProviderUserEntity user) {
        this.user = user;
    }
}
