package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MailProperties;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.exception.ResourceNotFoundException;
import hu.isakots.martosgym.rest.mail.model.EmailRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Service
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class.getName());

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailAddressExtractorService emailAddressExtractorService;

    @Value("${spring.profiles.active}")
    private Set<String> activeProfiles;

    public MailService(MailProperties mailProperties, JavaMailSender javaMailSender,
                       @Qualifier("emailTemplateEngine") TemplateEngine templateEngine,
                       EmailAddressExtractorService emailAddressExtractorService) {
        this.mailProperties = mailProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.emailAddressExtractorService = emailAddressExtractorService;
    }

    @Async
    void sendEmail(String to, String subject, String content) {
        if(activeProfiles.contains("no-mail")) {
           return;
        }
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

    public void sendRegistrationEmail(User user) {
        LOGGER.debug("Sending activation email to '{}'", user.getEmail());
        Context context = new Context();
        context.setVariable("user", user);
        String content = templateEngine.process("firstTemplate", context);
        String subject = "Registration success";
        sendEmail(user.getEmail(), subject, content);
    }

    void sendCustomEmailOneByOne(String email, String subject, String content) {
        LOGGER.debug("Sending customized email to '{}'", email);
        sendEmail(email, subject, content);
    }

    public void sendCustomEmail(EmailRequestModel model) {
       List<String> emailAddresses = emailAddressExtractorService.extractUserEmails(model.getMailTo());
       emailAddresses.forEach(email -> {
           sendCustomEmailOneByOne(email, model.getTopic(), model.getContent());
       });
    }

    public void sendCustomEmailToTrainingParticipants(Long trainingId, EmailRequestModel model) throws ResourceNotFoundException {
        List<String> emailAddresses = emailAddressExtractorService.extractTrainingParticipantEmails(trainingId);
        emailAddresses.forEach(email -> {
            sendCustomEmailOneByOne(email, model.getTopic(), model.getContent());
        });
    }



}
