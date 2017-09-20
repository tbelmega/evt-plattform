package de.belmega.eventers.user;

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
    ProviderService providerService;

    @Inject
    ScheduleEventService scheduleEventService;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    private EventProperties eventProperties = new EventProperties();

    private Optional<ProviderUserTO> provider;

    public void loadProfile() throws AuthException {
        if (FacesContext.getCurrentInstance().isPostback()) return;

        UserID idFromSession = (UserID) getHttpSession().getAttribute(ATTRIBUTE_USER_ID);
        this.provider = providerService.findProvider(idFromSession);

        if (!this.provider.isPresent()) getHttpSession().invalidate();

        List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(idFromSession);
        for (ScheduleEventEntity event : events) {
            ScheduleEvent scheduleEvent = createScheduleEvent(event);
            eventModel.addEvent(scheduleEvent);
        }

    }

    public void setProvider(ProviderUserTO provider) {
        this.provider = Optional.of(provider);
    }

    public ProviderUserTO getProvider() {
        return provider.get();
    }

    private HttpSession getHttpSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public Object save() {
        return null;
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
        //scheduleEventEntity.setUser(this.provider);
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
}
