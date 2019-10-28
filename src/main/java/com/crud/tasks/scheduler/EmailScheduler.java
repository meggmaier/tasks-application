package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    public static final String SUBJECT = "Tasks: Once a day email";
    public static final String MESSAGE = "Currently in database you got: ";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private MailCreatorService mailCreatorService;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail(){

        Mail mail = new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                mailCreatorService.buildInformationEmail(MESSAGE),
                "");

        simpleEmailService.send(mail);

    }
}
