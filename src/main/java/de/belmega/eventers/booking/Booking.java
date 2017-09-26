package de.belmega.eventers.booking;

public class Booking {
    private Object date;
    private Object time;
    private Object duration;
    private Object flexibility;
    private Object attendees;
    private Object location;
    private Object remark;
    private Object createInvoice;

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getDate() {
        return date;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getTime() {
        return time;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public Object getDuration() {
        return duration;
    }

    public void setFlexibility(Object flexibility) {
        this.flexibility = flexibility;
    }

    public Object getFlexibility() {
        return flexibility;
    }

    public Object getAttendees() {
        return attendees;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getLocation() {
        return location;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getRemark() {
        return remark;
    }

    public void setCreateInvoice(Object createInvoice) {
        this.createInvoice = createInvoice;
    }

    public Object getCreateInvoice() {
        return createInvoice;
    }
}
