package de.belmega.eventers.user;

import de.belmega.eventers.auth.AuthFilter;
import de.belmega.eventers.auth.LoginBean;
import de.belmega.eventers.scheduling.EventProperties;
import de.belmega.eventers.scheduling.ScheduleEventEntity;
import de.belmega.eventers.scheduling.ScheduleEventService;
import de.belmega.eventers.services.categories.ServiceCategoryId;
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
import java.util.*;

@Named
@SessionScoped
public class UserProfileBean implements Serializable {

    @Inject
    ProviderService providerService;

    @Inject
    ScheduleEventService scheduleEventService;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private DefaultScheduleEvent event = new DefaultScheduleEvent();
    private ProviderUserEntity provider;
    private List<String> repetitions = new ArrayList<>();
    private Date repeatUntil;


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
        repetitions = new ArrayList<>();
        repeatUntil = new Date();
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public boolean isInRoleFitness() {
        String fitness = ServiceCategoryId.SPORTS.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleCulinaric() {
        String fitness = ServiceCategoryId.CULINARIC.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleCulture() {
        String fitness = ServiceCategoryId.CULTURE.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleWellness() {
        String fitness = ServiceCategoryId.WELLNESS.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleMassage() {
        String fitness = ServiceCategoryId.MASSAGE.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleEntertainment() {
        String fitness = ServiceCategoryId.ENTERTAINMENT.name();
        return getProvider().getCategoryIds().contains(fitness);
    }

    public boolean isInRoleTransportation() {
        String fitness = ServiceCategoryId.TRANSPORTATION.name();
        return getProvider().getCategoryIds().contains(fitness);
    }
}
