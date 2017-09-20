package de.belmega.eventers.scheduling;

import java.util.Date;

public class EventProperties {
    private Boolean everyMonday;
    private Boolean everyTuesday;
    private Boolean everyWednesday;
    private Boolean everyThursday;
    private Boolean everyFriday;
    private Boolean everySaturday;
    private Boolean everySunday;
    private Date repeatUntil;

    public void setEveryMonday(Boolean everyMonday) {
        this.everyMonday = everyMonday;
    }

    public Boolean getEveryMonday() {
        return everyMonday;
    }

    public Boolean getEveryTuesday() {
        return everyTuesday;
    }

    public void setEveryTuesday(Boolean everyTuesday) {
        this.everyTuesday = everyTuesday;
    }

    public Boolean getEveryWednesday() {
        return everyWednesday;
    }

    public void setEveryWednesday(Boolean everyWednesday) {
        this.everyWednesday = everyWednesday;
    }

    public Boolean getEveryThursday() {
        return everyThursday;
    }

    public void setEveryThursday(Boolean everyThursday) {
        this.everyThursday = everyThursday;
    }

    public Boolean getEveryFriday() {
        return everyFriday;
    }

    public void setEveryFriday(Boolean everyFriday) {
        this.everyFriday = everyFriday;
    }

    public Boolean getEverySaturday() {
        return everySaturday;
    }

    public void setEverySaturday(Boolean everySaturday) {
        this.everySaturday = everySaturday;
    }

    public Boolean getEverySunday() {
        return everySunday;
    }

    public void setEverySunday(Boolean everySunday) {
        this.everySunday = everySunday;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public Date getRepeatUntil() {
        return repeatUntil;
    }
}
