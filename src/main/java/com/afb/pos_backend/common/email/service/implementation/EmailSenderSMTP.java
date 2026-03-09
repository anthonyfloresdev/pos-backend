package com.afb.pos_backend.common.email.service.implementation;

import com.afb.pos_backend.common.constant.MessageConstant;
import com.afb.pos_backend.common.email.service.EmailSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

@Component
@Log4j2
public class EmailSenderSMTP implements EmailSender {

    private final Session session;
    private final TemplateEngine templateEngine;
    private final String fromEmail;

    public EmailSenderSMTP(@Value("${app.smtp.host}") String host,
                           @Value("${app.smtp.port}") String port,
                           @Value("${app.smtp.username}") String username,
                           @Value("${app.smtp.password}") String password,
                           @Value("${app.smtp.from}") String fromEmail,
                           @Value("${app.smtp.secure:false}") boolean secure,
                           TemplateEngine templateEngine) {
        this.fromEmail = fromEmail;
        this.templateEngine = templateEngine;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        if (secure) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    @Async
    @Override
    public void sendHtmlEmail(String to, String subject, String templateName, Context context) {
        try {
            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject, "UTF-8");
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            logSuccessSendMessage(to);
        } catch (Exception ex) {
            logErrorSendingMessage(to, ex.getMessage());
        }
    }

    @Async
    @Override
    public void sendHtmlEmailWithLogo(String to, String subject, String templateName, Context context) {
        try {
            ClassPathResource logoResource = new ClassPathResource("static/logo.png");
            boolean logoExists = logoResource.exists();
            context.setVariable("logoExists", logoExists);

            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject, "UTF-8");

            if (logoExists) {
                MimeMultipart multipart = new MimeMultipart("related");

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
                multipart.addBodyPart(htmlPart);

                MimeBodyPart logoPart = new MimeBodyPart();
                byte[] logoBytes = logoResource.getInputStream().readAllBytes();
                DataSource logoDataSource = new ByteArrayDataSource(logoBytes, "image/png");
                logoPart.setDataHandler(new DataHandler(logoDataSource));
                logoPart.setHeader("Content-ID", "<logoImage>");
                logoPart.setHeader("Content-Type", "image/png");
                logoPart.setHeader("Content-Disposition", "inline");
                multipart.addBodyPart(logoPart);

                message.setContent(multipart);
            } else {
                message.setContent(htmlContent, "text/html; charset=UTF-8");
            }

            Transport.send(message);
            logSuccessSendMessageWithLogo(to);
        } catch (Exception ex) {
            logErrorSendingMessageWithLogo(to, ex.getMessage());
        }
    }

    @Override
    public void logSuccessSendMessage(Object... params) {
        log.info(MessageConstant.SUCCESS_SMTP_MESSAGE_SEND_HTML_EMAIL, params);
    }

    @Override
    public void logSuccessSendMessageWithLogo(Object... params) {
        log.info(MessageConstant.SUCCESS_SMTP_MESSAGE_SEND_HTML_EMAIL_WITH_LOGO, params);
    }

    @Override
    public void logErrorSendingMessage(String to, String exceptionMessage) {
        log.error(MessageConstant.ERROR_MESSAGE_SEND_HTML_EMAIL, to, exceptionMessage);
    }

    @Override
    public void logErrorSendingMessageWithLogo(String to, String exceptionMessage) {
        log.error(MessageConstant.ERROR_MESSAGE_SEND_HTML_EMAIL_WITH_LOGO, to, exceptionMessage);
    }
}
