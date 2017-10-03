package de.belmega.eventers.booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Booking")
public class BookingEntity {

    @Id
    @GeneratedValue
    private long id;
    private String location;
    private Boolean createInvoice;

    @Column(nullable = false)
    private Date preferredStartTime = new Date();

    @Column(nullable = false)
    private EventDuration duration = EventDuration.MIN_90;

    @Column(nullable = false)
    private EventFlexibility flexibility = EventFlexibility.NONE;

    private Integer numberOfAttendees;
    private String remark;

    @Column(nullable = false)
    private Date latestStartTime = new Date();


    @Column(nullable = false)
    private Date earliestEndTime = new Date();

    public void setNumberOfAttendees(Integer numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public Integer getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setCreateInvoice(Boolean createInvoice) {
        this.createInvoice = createInvoice;
    }

    public Boolean getCreateInvoice() {
        return createInvoice;
    }

    public void setPreferredStartTime(Date preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }

    public Date getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setDuration(EventDuration duration) {
        this.duration = duration;
    }

    public EventDuration getDuration() {
        return duration;
    }

    public void setFlexibility(EventFlexibility flexibility) {
        this.flexibility = flexibility;
    }

    public EventFlexibility getFlexibility() {
        return flexibility;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLatestStartTime(Date latestStartTime) {
        this.latestStartTime = latestStartTime;
    }

    public Date getLatestStartTime() {
        return latestStartTime;
    }

    public Date getEarliestEndTime() {
        return earliestEndTime;
    }

    public void setEarliestEndTime(Date earliestEndTime) {
        this.earliestEndTime = earliestEndTime;
    }
}
