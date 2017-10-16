package de.belmega.eventers.mail;

import de.belmega.eventers.booking.BookingDAO;
import de.belmega.eventers.booking.BookingEntity;
import de.belmega.eventers.booking.BookingLinkDAO;
import de.belmega.eventers.booking.BookingLinkEntity;
import de.belmega.eventers.user.ProviderUserEntity;
import de.belmega.eventers.user.registration.RegisterProviderBean;
import de.mirkosertic.cdicron.api.Cron;
import io.undertow.util.FileUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.jboss.logging.Logger;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class checks on a scheduled base if there are emails that need to be send.
 * more information about Cron expressions: https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
 */
@ApplicationScoped
public class FixedTimerEmailSendBean {

    /**
     * Cron expression to send mails every hour.
     */
    public static final String SEND_EMAIL_INTERVALL = "0 0/15 * * * ?";

    @Inject
    @ConfigurationValue("urls.hostname")
    String hostname;

    @Inject
    BookingDAO bookingDAO;

    @Inject
    BookingLinkDAO bookingLinkDAO;

    @Inject
    EmailSessionBean emailSessionBean;

    private Logger logger = Logger.getLogger(FixedTimerEmailSendBean.class);

    @Cron(cronExpression = SEND_EMAIL_INTERVALL)
    public void atSchedule() throws InterruptedException {
        logger.info("Sending scheduled emails.");

        sendMailsToCheckIfEventWasAccomplished();
    }

    private void sendMailsToCheckIfEventWasAccomplished() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -30);
        Date endTimeSlot = cal.getTime();
        cal.add(Calendar.MINUTE, -60);
        Date beginTimeSlot = cal.getTime();

        List<BookingEntity> bookingsStartingBetween = bookingDAO.findBookingsStartingBetween(beginTimeSlot, endTimeSlot);

        for (BookingEntity be: bookingsStartingBetween) {
            if (be.isAccepted()) sendMailToCheckIfEventWasAccomplished(be);
            //else bookingDAO.remove(be); // TODO remove old bookings here?
        }
    }

    private void sendMailToCheckIfEventWasAccomplished(BookingEntity bookingEntity) {
        String emailadress = bookingEntity.getProvider().getEmailadress();

        InputStream mailText = RegisterProviderBean.class.getClassLoader().getResourceAsStream("emails/evaluation.txt");
        String mailTextString = FileUtils.readFile(mailText);

        ProviderUserEntity provider = bookingEntity.getProvider();

        Map<String, String> data = new HashMap<>();
        data.put("greeting-provider", provider.getGreeting().getGreeting() + " " + provider.getLastname());

        BookingLinkEntity link = bookingLinkDAO.findLinkForBooking(bookingEntity, provider);

        String fullLink = hostname + "/evaluate-event.xhtml?id=" + link.getId();
        data.put("link", fullLink);

        String formattedMailText = StrSubstitutor.replace(mailTextString, data);
        System.out.println(formattedMailText);

        emailSessionBean.sendEmail(provider.getEmailadress(), "the-eventers.de - Hat Ihr Termin stattgefunden?", formattedMailText);
    }


}
