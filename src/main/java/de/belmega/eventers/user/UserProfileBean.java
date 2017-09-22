package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.auth.LoginBean;
import de.belmega.eventers.scheduling.EventProperties;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.scheduling.ScheduleEventService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
public class UserProfileBean implements Serializable {

    @Inject
    LoginBean loginBean;

    @Inject
    ProviderService providerService;

    @Inject
    ScheduleEventService scheduleEventService;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    private EventProperties eventProperties = new EventProperties();
    private ProviderUserEntity provider;


    public void save() {
        //TODO update entity?
    }


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
        scheduleEventService.persistEvent(createEventEntity(event));

        event = new DefaultScheduleEvent();
        eventProperties = new EventProperties();
    }

    private ScheduleEventEntity createEventEntity(ScheduleEvent event) {
        ScheduleEventEntity scheduleEventEntity = new ScheduleEventEntity(event.getId(), event.getTitle(),
                event.getStartDate(), event.getEndDate());
        Optional<ProviderUserEntity> userEntity = providerService.userDAO.findById(this.provider.getId());
        scheduleEventEntity.setUser(userEntity.get());
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
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void setEventProperties(EventProperties eventProperties) {
        this.eventProperties = eventProperties;
    }

    public EventProperties getEventProperties() {
        return eventProperties;
    }

    public void setProvider(ProviderUserEntity provider) {
        this.provider = provider;
    }

    public ProviderUserEntity getProvider() {
        if (this.provider == null) this.provider = loadProfile();

        return this.provider;
    }

    private ProviderUserEntity loadProfile() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        UserID userId = (UserID) session.getAttribute(AuthFilter.ATTRIBUTE_USER_ID);
        Optional<ProviderUserEntity> providerUserEntity = providerService.findById(userId);

        if (!providerUserEntity.isPresent()) throw new RuntimeException("Trying to load a user that does not exist.");

        ProviderUserEntity userEntity = providerUserEntity.get();

        List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(userEntity.getId());
        for (ScheduleEventEntity event : events) {
            ScheduleEvent scheduleEvent = createScheduleEvent(event);
            eventModel.addEvent(scheduleEvent);
        }

        return userEntity;
    }

}
