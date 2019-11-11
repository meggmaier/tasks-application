package com.crud.tasks.service;



import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailCreatorServiceTest {

    @Autowired
    MailCreatorService mailCreatorService;

    @Test
    public void buildTrelloCardEmailTest() throws IOException {
        //Given
        URL url = Resources.getResource("trello-card-mail.txt");
        String text = Resources.toString(url, Charsets.UTF_8);
        //When
        String message = "Test message";
        String outputHtml = mailCreatorService.buildTrelloCardEmail(message);
        //Then
        System.out.println(outputHtml);
        assertEquals(text, outputHtml);
    }

    @Test
    public void buildInformationEmailTest() throws IOException{
        //Given
        URL url = Resources.getResource("info-mail.txt");
        String text = Resources.toString(url, Charsets.UTF_8);
        //When
        String message = "Test message";
        String outputHtml = mailCreatorService.buildInformationEmail(message);
        //Then
        System.out.println(outputHtml);
        assertEquals(text, outputHtml);
    }

}
