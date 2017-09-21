package de.belmega.eventers.user;

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
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static de.belmega.eventers.auth.AuthFilter.ATTRIBUTE_USER_ID;

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
    private ProviderUserTO provider;


    public void loadProfile() {
        UserID id = loginBean.getSessionUser().getId();

        this.provider = providerService.findProvider(id).get();

        List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(id);
        for (ScheduleEventEntity event : events) {
            ScheduleEvent scheduleEvent = createScheduleEvent(event);
            eventModel.addEvent(scheduleEvent);
        }

    }

    public void save() {
        providerService.update(this.provider);
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

    public void setProvider(ProviderUserTO provider) {
        this.provider = provider;
    }

    public ProviderUserTO getProvider() {
        if (this.provider == null) loadProfile();

        return provider;
    }
}
