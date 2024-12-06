package com.stagora.services;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceMailing {

    @Value("${config.mail.sender_mail}")
    private String SENDER_MAIL;

    @Value("${config.mail.sender_password}")
    private String SENDER_PASSWORD;

    @Value("${config.mail.host}")
    private String HOST_MAIL;

    @Value("${config.mail.port}")
    private String PORT_MAIL;

    private static final Logger log = LoggerFactory.getLogger(ServiceMailing.class);

    private static final String DEFAULT_CONFIRMATION_SUBJECT = "Confirmation inscription";

    @Async
    public void confirmationInscription(String email, String contenu) {
        String subject = "Confirmation inscription";
        sendMail(email, subject, contenu);
    }

    @Async
    public void sendCustomEmail(String email, String subject, String contenu) {
        // Permet d'envoyer un email avec un contenu personnalisé
        sendMail(email, subject, contenu);
    }

    private void sendMail(String dest, String object, String text) {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");

        // Propriétés pour le serveur SMTP
        Properties propMail = new Properties();
        propMail.put("mail.smtp.auth", "true");
        propMail.put("mail.smtp.starttls.enable", "true");
        propMail.put("mail.smtp.host", HOST_MAIL);
        propMail.put("mail.smtp.port", PORT_MAIL);
        propMail.put("mail.smtp.ssl.trust", HOST_MAIL);
        propMail.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_MAIL, SENDER_PASSWORD);
            }
        };

        try {
            // Créer une session
            Session session = Session.getInstance(propMail, auth);

            // Créer un message
            Message message = new MimeMessage(session);

            // Ajout du sender
            message.setFrom(new InternetAddress(SENDER_MAIL));

            // Ajout du destinataire
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));

            // Ajout de l'objet
            message.setSubject(object);

            // Ajout du corps de l'email
            message.setContent(text, "text/html; charset=UTF-8");

            // Envoi du message
            Transport.send(message);
            log.info("Email envoyé avec succès à {}", dest);

        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email à {} : {}", dest, e.getMessage());
        }
    }
}
