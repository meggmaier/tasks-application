package com.crud.tasks.service;

import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.scheduler.EmailScheduler;
import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.config.CompanyConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
@Getter
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TaskRepository taskRepository;

    private Context trelloCardMailContext;

    private Context dailyInfoMailContext;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        trelloCardMailContext = new Context();
        trelloCardMailContext.setVariable("message", message);
        trelloCardMailContext.setVariable("tasks_url", "https://meggmaier.github.io/");
        trelloCardMailContext.setVariable("button", "Visit website");
        trelloCardMailContext.setVariable("admin_name", adminConfig.getAdminName());
        trelloCardMailContext.setVariable("goodbye", "Sincerely, " + companyConfig.getName());
        trelloCardMailContext.setVariable("company_details", companyConfig.getName() + ", email: " + companyConfig.getEmail() + ", phone: " + companyConfig.getPhone());
        trelloCardMailContext.setVariable("show_button", false);
        trelloCardMailContext.setVariable("is_friend", false);
        trelloCardMailContext.setVariable("admin_config", adminConfig);
        trelloCardMailContext.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", trelloCardMailContext);
    }

    public String buildInformationEmail(String message) {

        dailyInfoMailContext = new Context();
        dailyInfoMailContext.setVariable("message", message);
        dailyInfoMailContext.setVariable("title", EmailScheduler.SUBJECT);
        dailyInfoMailContext.setVariable("tasks_url", "https://meggmaier.github.io/");
        dailyInfoMailContext.setVariable("button", "Visit website");
        dailyInfoMailContext.setVariable("admin_name", adminConfig.getAdminName());
        dailyInfoMailContext.setVariable("size", taskRepository.count());
        dailyInfoMailContext.setVariable("is_singular", taskRepository.count() == 1);
        dailyInfoMailContext.setVariable("goodbye", "Sincerely, " + companyConfig.getName());
        dailyInfoMailContext.setVariable("company_details", companyConfig.getName() + ", email: " + companyConfig.getEmail() + ", phone: " + companyConfig.getPhone());
        return templateEngine.process("mail/daily-info-mail", dailyInfoMailContext);
    }
}