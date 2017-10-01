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
import java.util.*;

@ManagedBean
@SessionScoped
public class CalendarBean {

    @Inject
    UserProfileBean userProfileBean;

    @Inject
    ScheduleEventService scheduleEventService;


    private List<Integer> repetitions = new ArrayList<>();
    private Date repeatUntil;

    private ScheduleModel eventModel;

    private DefaultScheduleEvent event = new DefaultScheduleEvent();

    private ProviderUserEntity providerUserEntity;


    private ProviderUserEntity getProvider() {
        if (providerUserEntity == null) {
            providerUserEntity = userProfileBean.getProvider();
        }
        return providerUserEntity;
    }


    public ScheduleModel getEventModel() {
        if (eventModel == null) {
            eventModel = new DefaultScheduleModel();

            List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(getProvider().getId());

            for (ScheduleEventEntity event : events) {
                ScheduleEvent scheduleEvent = createScheduleEvent(event);
                eventModel.addEvent(scheduleEvent);
            }
        }

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

        if (!repetitions.isEmpty())
            calculateRepitition(event);

        event = new DefaultScheduleEvent();
    }

    private void calculateRepitition(DefaultScheduleEvent event) {
        Calendar cal = Calendar.getInstance();
        Date startDate = event.getStartDate();
        cal.setTime(startDate);

        for (int i = 0; i <= 7; i++) {
            cal.add(Calendar.DATE, 1);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (repetitions.contains(dayOfWeek)) {
                System.out.println(cal.getTime());
            }

        }

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
        scheduleEventService.deleteEvent(this.event);
        eventModel.deleteEvent(this.event);
    }

    public List<Integer> getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(List<Integer> repetitions) {
        this.repetitions = repetitions;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public Date getRepeatUntil() {
        return repeatUntil;
    }

}
