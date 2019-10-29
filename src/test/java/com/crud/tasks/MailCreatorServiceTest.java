package com.crud.tasks;



import com.crud.tasks.service.MailCreatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        //Then
        System.out.println(outputHtml);
    }

    @Test
    public void buildInformationEmailTest(){
        //Given
        //When
        String message = "Test message";
        String outputHtml = mailCreatorService.buildInformationEmail(message);
        //Then
        System.out.println(outputHtml);
    }

}
