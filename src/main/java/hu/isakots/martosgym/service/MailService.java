package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MailProperties;
import hu.isakots.martosgym.domain.Training;
import hu.isakots.martosgym.domain.User;
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

import static hu.isakots.martosgym.service.util.Constants.*;

@Service
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class.getName());
    private static final String USER_KEY = "user";
    private static final String PROFILE_URL_KEY = "profileUrl";
    private static final String ARTICLE_URL_KEY = "articleUrl";
    private static final String TRAININGS_URL_KEY = "trainingsUrl";
    private static final String TRAININGS_TIME_KEY = "trainingTime";
    private static final String TRAININGS_NAME_KEY = "trainingName";

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final AccountService accountService;
    private final LinkBuilderService linkBuilderService;

    @Value("${spring.profiles.active}")
    private Set<String> activeProfiles;

    public MailService(MailProperties mailProperties, JavaMailSender javaMailSender,
                       @Qualifier("emailTemplateEngine") TemplateEngine templateEngine, AccountService accountService,
                       LinkBuilderService linkBuilderService) {
        this.mailProperties = mailProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.accountService = accountService;
        this.linkBuilderService = linkBuilderService;
    }

    @Async
    void sendEmail(String to, String subject, String content) {
        if (activeProfiles.contains("no-mail")) {
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
        context.setVariable(USER_KEY, user);
        String content = templateEngine.process("registration", context);
        String subject = TITLE_OF_REGISTRATION_EMAIL_NOTIFICATION;
        sendEmail(user.getEmail(), subject, content);
    }

    void sendNotificationEmailOnNewArticle(User user) {
        LOGGER.debug("Sending notification email about new article to '{}'", user.getEmail());
        Context context = new Context();
        context.setVariable(USER_KEY, user);
        context.setVariable(ARTICLE_URL_KEY, linkBuilderService.createHomePageUrl());
        context.setVariable(PROFILE_URL_KEY, linkBuilderService.createProfilePageUrl());
        String content = templateEngine.process("notificationOnNewArticle", context);
        String subject = TITLE_OF_NEW_ARTICLE_EMAIL_NOTIFICATION;
        sendEmail(user.getEmail(), subject, content);
    }

    void sendNotificationEmailOnNewTraining(User user) {
        LOGGER.debug("Sending notification email about new training to '{}'", user.getEmail());
        Context context = new Context();
        context.setVariable(USER_KEY, user);
        context.setVariable(TRAININGS_URL_KEY, linkBuilderService.createTrainingsPageUrl());
        context.setVariable(PROFILE_URL_KEY, linkBuilderService.createProfilePageUrl());
        String content = templateEngine.process("notificationOnNewTraining", context);
        String subject = TITLE_OF_NEW_TRAINING_EMAIL_NOTIFICATION;
        sendEmail(user.getEmail(), subject, content);
    }

    public void sendNotificationEmailOnSubscribedTraining(User user, Training training) {
        LOGGER.debug("Sending notification email about subscribed training to '{}'", user.getEmail());
        Context context = new Context();
        context.setVariable(USER_KEY, user);
        context.setVariable(TRAININGS_NAME_KEY, training.getName());
        context.setVariable(TRAININGS_TIME_KEY, linkBuilderService.createTrainingsPageUrl());
        context.setVariable(PROFILE_URL_KEY, linkBuilderService.createProfilePageUrl());
        String content = templateEngine.process("notificationOnSubscribedTraining", context);
        String subject = TITLE_OF_SUBSCRIBED_TRAINING_EMAIL_NOTIFICATION;
        sendEmail(user.getEmail(), subject, content);
    }

    @Async
    public void sendCustomEmail(EmailRequestModel model) {
        List<String> emailAddresses = accountService.extractUserEmails(model.getMailTo());
        emailAddresses.forEach(email -> sendCustomEmailOneByOne(email, model.getTopic(), model.getContent()));
    }

    @Async
    public void startEmailNotificationAsyncTaskOnNewArticle() {
        List<User> subscribers = accountService.extractUsersWithNewArticleSubscription();
        subscribers.forEach(this::sendNotificationEmailOnNewArticle);
    }

    @Async
    public void startEmailNotificationAsyncTaskOnNewTraining() {
        List<User> subscribers = accountService.extractUsersWithNewTrainingSubscription();
        subscribers.forEach(this::sendNotificationEmailOnNewTraining);
    }

    private void sendCustomEmailOneByOne(String email, String subject, String content) {
        LOGGER.debug("Sending customized email to '{}'", email);
        sendEmail(email, subject, content);
    }

}
