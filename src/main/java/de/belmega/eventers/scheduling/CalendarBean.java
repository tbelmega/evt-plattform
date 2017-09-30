package de.belmega.eventers.scheduling;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ManagedBean
@SessionScoped
public class CalendarBean {

    @Inject
    UserProfileBean userProfileBean;

    @Inject
    ScheduleEventService scheduleEventService;


    private List<String> repetitions = new ArrayList<>();
    private Date repeatUntil;

    private ProviderUserEntity providerUserEntity;

    private ProviderUserEntity getProvider(){

        if (providerUserEntity == null) {

            providerUserEntity = userProfileBean.getProvider();

            List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(providerUserEntity.getId());
            for (ScheduleEventEntity event : events) {
                ScheduleEvent scheduleEvent = createScheduleEvent(event);
                eventModel.addEvent(scheduleEvent);
            }
        }
        return providerUserEntity;
    }

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private DefaultScheduleEvent event = new DefaultScheduleEvent();


    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(DefaultScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {

        if (event.getId() == null) {
            event.setTitle("Verfügbar");
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        ScheduleEventEntity eventEntity = createEventEntity(event);
        scheduleEventService.persistEvent(eventEntity);

        calculateRepitition();

        event = new DefaultScheduleEvent();
    }

    private void calculateRepitition() {
        //TODO
        System.out.println(repetitions);
        System.out.println(repeatUntil);
    }

    private ScheduleEventEntity createEventEntity(ScheduleEvent event) {
        ScheduleEventEntity scheduleEventEntity = new ScheduleEventEntity(event.getId(), event.getTitle(),
                event.getStartDate(), event.getEndDate());

        scheduleEventEntity.setUser(getProvider());
        return scheduleEventEntity;
    }

    private ScheduleEvent createScheduleEvent(ScheduleEventEntity event) {
        DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(event.getTitle(), event.getStartDate(), event.getEndDate());
        scheduleEvent.setId(event.getId());
        return scheduleEvent;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (DefaultScheduleEvent) selectEvent.getObject();
    }

    public void onEventResize(SelectEvent selectEvent) {
        System.out.println(event);
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        repetitions = new ArrayList<>();
        repeatUntil = new Date();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        scheduleEventService.deleteEvent(this.event.getId());
        eventModel.deleteEvent(this.event);
    }

    public List<String> getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(List<String> repetitions) {
        this.repetitions = repetitions;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public Date getRepeatUntil() {
        return repeatUntil;
    }

}
