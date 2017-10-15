package de.belmega.eventers.mail;

import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Stateless
public class EmailSessionBean {

    private EmailAdress registration = new EmailAdress();


    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", registration.getHost());
        props.put("mail.smtp.port", registration.getPort());
        props.put("mail.smtp.starttls.enable", true);

        Authenticator authenticator = null;
        if (registration.isAuth()) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(registration.getUsername(), registration.getPassword());
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(registration.isDebug());


        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(registration.getFrom()));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }


}
