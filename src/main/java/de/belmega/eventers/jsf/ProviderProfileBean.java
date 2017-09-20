package de.belmega.eventers.jsf;

import de.belmega.eventers.dto.EventProperties;
import de.belmega.eventers.dto.UserID;
import de.belmega.eventers.dto.ServiceProviderUserTO;
import de.belmega.eventers.persistence.entities.ScheduleEventEntity;
import de.belmega.eventers.service.ProviderService;
import de.belmega.eventers.service.ScheduleEventService;
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

import static de.belmega.eventers.filter.AuthFilter.ATTRIBUTE_USER_ID;


@Named
@SessionScoped
public class ProviderProfileBean implements Serializable {

    @Inject
    ProviderService providerService;

    @Inject
    ScheduleEventService scheduleEventService;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    private EventProperties eventProperties = new EventProperties();

    private ServiceProviderUserTO provider;

    private String serviceProviderId;


    public void loadProfile() throws AuthException {
        if (FacesContext.getCurrentInstance().isPostback()) return;

//        String idFromSession = (String) getHttpSession().getAttribute(ATTRIBUTE_USER_ID);
//        if(!serviceProviderId.equals(idFromSession)){
//            System.out.println(idFromSession);
//            throw new AuthException("Zugriff auf dieses Nutzerprofil ist nicht gestattet.");
//        }

        UserID id = new UserID(serviceProviderId);
        this.provider = providerService.findProvider(id);


        List<ScheduleEventEntity> events = scheduleEventService.findEventsByUser(id);
        for (ScheduleEventEntity event: events) {
            ScheduleEvent scheduleEvent = createScheduleEvent(event);
            eventModel.addEvent(scheduleEvent);
        }

    }



    public void setProvider(ServiceProviderUserTO provider) {
        this.provider = provider;
    }

    public ServiceProviderUserTO getProvider() {
        return provider;
    }

    public void setServiceProviderId(String serviceProviderId) {
        System.out.println("ID set from URL: " + serviceProviderId);
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
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
        if(event.getId() == null) {
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
