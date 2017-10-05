package de.belmega.eventers.scheduling;

import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.UserProfileBean;
import de.belmega.eventers.util.DateUtil;
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


    private List<String> repetitions = new ArrayList<>();
    private Date repeatUntil;

    private ScheduleModel eventModel;

    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    DefaultScheduleEvent previousEvent = event;


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

        previousEvent = event;
        event = new DefaultScheduleEvent();
    }

    private void calculateRepitition(DefaultScheduleEvent event) {
        List<Integer> repititionsAsIntegers = getRepetitionsAsIntegers(repetitions);
        Calendar cal = Calendar.getInstance();
        Date startDate = event.getStartDate();

        if (repeatUntil == null)
            repeatUntil = defaultEndDate(cal, startDate);

        cal.setTime(startDate);

        for (int i = 1; i <= 7; i++) {
            cal.add(Calendar.DATE, 1);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (repititionsAsIntegers.contains(dayOfWeek))
                addEventSeries(cal.getTime(), event, repeatUntil);
        }
    }

    /**
     * From the given startDate, add a copy of the event every week until the endOfRepititionDate.
     */
    private void addEventSeries(Date startDate, DefaultScheduleEvent event, Date endOfRepititionDate) {

        while (startDate.getTime() < endOfRepititionDate.getTime()) {

            Date endTime = new Date(startDate.getTime());
            endTime.setHours(event.getEndDate().getHours());
            endTime.setMinutes(event.getEndDate().getMinutes());

            DefaultScheduleEvent nextEvent = new DefaultScheduleEvent();
            nextEvent.setStartDate(startDate);
            nextEvent.setEndDate(endTime);
            nextEvent.setTitle(event.getTitle());
            nextEvent.setData(event.getData());
            nextEvent.setDescription(event.getDescription());

            eventModel.addEvent(nextEvent);
            ScheduleEventEntity eventEntity = createEventEntity(nextEvent);
            scheduleEventService.persistEvent(eventEntity);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            startDate = calendar.getTime();
        }
    }

    /**
     * Set three month after start date as default end date for the repitition.
     */
    private Date defaultEndDate(Calendar cal, Date startDate) {
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }

    private List<Integer> getRepetitionsAsIntegers(List<String> repetitions) {
        List<Integer> result = new ArrayList<>();
        for (String s: repetitions)
            result.add(Integer.parseInt(s));

        return result;
    }

    private ScheduleEventEntity createEventEntity(ScheduleEvent event) {
        Date endDate = DateUtil.combineDateTime(event.getStartDate(), event.getEndDate());

        ScheduleEventEntity scheduleEventEntity = new ScheduleEventEntity(event.getId(), event.getTitle(),
                event.getStartDate(), endDate);

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
        if (previousEvent != null && previousEvent.getId() != null) {
            event.getStartDate().setHours(previousEvent.getStartDate().getHours());
            event.getEndDate().setHours(previousEvent.getEndDate().getHours());

            event.getStartDate().setMinutes(previousEvent.getStartDate().getMinutes());
            event.getEndDate().setMinutes(previousEvent.getEndDate().getMinutes());
        }

        repetitions = new ArrayList<>();
        repeatUntil = new Date();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        scheduleEventService.deleteEvent(this.event);
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
