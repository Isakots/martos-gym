package hu.isakots.martosgym.service;

import hu.isakots.martosgym.configuration.properties.MailProperties;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {
    private static final String MOCK_MAIL_TO = "MOCK_MAIL_TO";
    private static final String MOCK_SUBJECT = "MOCK_SUBJECT";
    private static final String MOCK_CONTENT = "MOCK_CONTENT";
    private static final String MOCK_HOST = "MOCK_HOST";

    @Mock
    private MailProperties mailProperties;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private AccountService accountService;
    @Mock
    private LinkBuilderService linkBuilderService;

    @InjectMocks
    private MailService mailService;

    @Before
    public void init() {
        ReflectionTestUtils.setField(mailService, "activeProfiles", Sets.newHashSet(Collections.singletonList("dev")));
    }

    @Test
    public void sendEmail() {
        MimeMessage message = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        when(mailProperties.getHost()).thenReturn(MOCK_HOST);

        mailService.sendEmail(MOCK_MAIL_TO, MOCK_SUBJECT, MOCK_CONTENT);

        verify(javaMailSender).send(eq(message));
        verify(templateEngine).clearTemplateCache();
    }

}