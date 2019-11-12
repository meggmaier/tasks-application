package com.crud.tasks.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.Mail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SimpleEmailService.class);
    private ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @Before
    public void setUpLogger(){
        listAppender.start();
        LOGGER.addAppender(listAppender);
    }

    @Test
    public void shouldSendEmail(){
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test message.", "");
        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(any(MimeMessagePreparator.class));
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Email has been sent.", logsList.get(0).getMessage());
        assertEquals(Level.INFO, logsList.get(0).getLevel());
    }

    @Test
    public void verifyCatchIsWorking(){
        //Given
        String exceptionMessage = "Failed to send test message to test@test.com";
        Mail mail = new Mail("test@test.com", "Test", "Test message.", "");
        doThrow(new MailAuthenticationException(exceptionMessage)).when(javaMailSender).send(any(MimeMessagePreparator.class));

        //When
        simpleEmailService.send(mail);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Failed to process email sending: Failed to send test message to test@test.com",
                    logsList.get(0).getFormattedMessage());
        assertEquals(Level.ERROR, logsList.get(0).getLevel());
    }
}