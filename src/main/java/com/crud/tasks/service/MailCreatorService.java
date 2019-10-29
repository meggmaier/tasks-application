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

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://meggmaier.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Sincerely, " + companyConfig.getName());
        context.setVariable("company_details", companyConfig.getName() + ", email: " + companyConfig.getEmail() + ", phone: " + companyConfig.getPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildInformationEmail(String message) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("title", EmailScheduler.SUBJECT);
        context.setVariable("tasks_url", "https://meggmaier.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("size", taskRepository.count());
        context.setVariable("is_singular", taskRepository.count() == 1);
        context.setVariable("goodbye", "Sincerely, " + companyConfig.getName());
        context.setVariable("company_details", companyConfig.getName() + ", email: " + companyConfig.getEmail() + ", phone: " + companyConfig.getPhone());
        return templateEngine.process("mail/daily-info-mail", context);
    }
}