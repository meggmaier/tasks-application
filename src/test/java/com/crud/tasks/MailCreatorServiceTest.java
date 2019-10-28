package com.crud.tasks;



import com.crud.tasks.service.MailCreatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailCreatorServiceTest {

    @Autowired
    MailCreatorService mailCreatorService;

    @Test
    public void buildTrelloCardEmailTest(){
        //Given
        //When
        String message = "Test message";
        String outputHtml = mailCreatorService.buildTrelloCardEmail(message);
        Context trelloCardMailContext = mailCreatorService.getTrelloCardMailContext();
        //Then
        System.out.println(outputHtml);
        assertEquals(trelloCardMailContext.getVariable("message"), message);
        assertEquals(trelloCardMailContext.getVariable("admin_name"), "Kodilla User");
        assertEquals(trelloCardMailContext.getVariable("goodbye"), "Sincerely, TaskCrudAppCreator" );
    }

    @Test
    public void buildInformationEmailTest(){
        //Given
        //When
        String message = "Test message";
        String outputHtml = mailCreatorService.buildInformationEmail(message);
        Context dailyInfoMailContext = mailCreatorService.getDailyInfoMailContext();
        //Then
        System.out.println(outputHtml);
        assertEquals(dailyInfoMailContext.getVariable("message"), message);
        assertEquals(dailyInfoMailContext.getVariable("title"), "Tasks: Once a day email");
        assertEquals(dailyInfoMailContext.getVariable("admin_name"), "Kodilla User");
        assertEquals(dailyInfoMailContext.getVariable("size"), 0L);
        assertFalse((Boolean) dailyInfoMailContext.getVariable("is_singular"));
    }

}
