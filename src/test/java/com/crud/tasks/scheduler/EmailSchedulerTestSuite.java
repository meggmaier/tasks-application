package com.crud.tasks.scheduler;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailSchedulerTestSuite {

    @InjectMocks
    EmailScheduler emailScheduler;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private MailCreatorService mailCreatorService;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    public void shouldSentInfoMail() throws IOException {
        //Given
        String text = "Test message";
        ArgumentCaptor<Mail> argumentCaptor = ArgumentCaptor.forClass(Mail.class);
        Mail mail = new Mail("test@test.com", EmailScheduler.SUBJECT, text, "");

        //When
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        when(mailCreatorService.buildInformationEmail(anyString())).thenReturn(text);

        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).send(any(Mail.class));
        verify(simpleEmailService).send(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getMessage(), mail.getMessage());
        assertEquals(argumentCaptor.getValue().getMailTo(), mail.getMailTo());
        assertEquals(argumentCaptor.getValue().getSubject(), mail.getSubject());
        assertEquals(argumentCaptor.getValue().getToCc(), mail.getToCc());
    }
}
