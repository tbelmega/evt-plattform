package de.belmega.eventers.booking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Booking")
public class BookingEntity {

    @Id
    @GeneratedValue
    private long id;
    private String location;
    private Boolean createInvoice;
    private Date dateTime;
    private EventDuration duration;
    private EventFlexibility flexibility;
    private Integer numberOfAttendees;
    private String remark;

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

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
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
}
