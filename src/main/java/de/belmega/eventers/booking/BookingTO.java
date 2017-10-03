package de.belmega.eventers.booking;

import java.util.Date;

public class BookingTO {
    private Long id;
    private Date date = new Date();
    private Date time;
    private EventDuration duration;
    private EventFlexibility flexibility;
    private Integer attendees;
    private String location;
    private String remark;
    private Boolean createInvoice;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
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

    public Integer getAttendees() {
        return attendees;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreateInvoice(Boolean createInvoice) {
        this.createInvoice = createInvoice;
    }

    public Boolean getCreateInvoice() {
        return createInvoice;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
