package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MailProperties;
import hu.isakots.martosgym.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {
    private final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailService(MailProperties mailProperties, JavaMailSender javaMailSender,
                       @Qualifier("emailTemplateEngine") TemplateEngine templateEngine) {
        this.mailProperties = mailProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setFrom(mailProperties.getHost());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, true);
            javaMailSender.send(mimeMessage);
            LOGGER.debug("Sent email to User '{}'", to);
            this.templateEngine.clearTemplateCache();
        } catch (Exception e) {
            LOGGER.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendRegistrationEmail(User user) {
        LOGGER.debug("Sending activation email to '{}'", user.getEmail());
        Context context = new Context();
        context.setVariable("user", user);
        String content = templateEngine.process("firstTemplate", context);
        String subject = "Registration success";
        sendEmail(user.getEmail(), subject, content);
    }

}
