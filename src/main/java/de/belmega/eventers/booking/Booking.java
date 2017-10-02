package de.belmega.eventers.booking;

import java.util.Date;

public class Booking {
    private Date date;
    private Date time;
    private String duration;
    private String flexibility;
    private String attendees;
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

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setFlexibility(String flexibility) {
        this.flexibility = flexibility;
    }

    public String getFlexibility() {
        return flexibility;
    }

    public String getAttendees() {
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

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }
}
