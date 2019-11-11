package com.crud.tasks.validator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTestSuite {

    @Autowired
    TrelloValidator trelloValidator;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
    private ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @Before
    public void setUpLogger(){
        listAppender.start();
        LOGGER.addAppender(listAppender);
    }

    @Test
    public void validateTestCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "testing", "bottom", "001");

        //When
        trelloValidator.validateCard(trelloCard);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Someone is testing my application!", logsList.get(0).getMessage());
        assertEquals(Level.INFO, logsList.get(0).getLevel());
    }

    @Test
    public void validateNonTestCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Task to do", "Do some tests!", "up", "002");

        //When
        trelloValidator.validateCard(trelloCard);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals("Seems that my application is used in proper way.", logsList.get(0).getMessage());
        assertEquals(Level.INFO, logsList.get(0).getLevel());
    }

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloBoard> listToValidate = new ArrayList<>();
        listToValidate.add(new TrelloBoard("01", "test", new ArrayList<>()));
        listToValidate.add(new TrelloBoard("02", "test", new ArrayList<>()));
        listToValidate.add(new TrelloBoard("03", "test", new ArrayList<>()));

        //When
        List<TrelloBoard> expectedList = trelloValidator.validateTrelloBoards(listToValidate);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(0, expectedList.size());
        assertEquals(Level.INFO, logsList.get(1).getLevel());
        assertEquals("Boards have been filtered. Current list size: 0", logsList.get(1).getMessage());
    }

    @Test
    public void shouldFetchFilteredBoards() {
        //Given
        List<TrelloBoard> listToValidate = new ArrayList<>();
        listToValidate.add(new TrelloBoard("01", "test", new ArrayList<>()));
        listToValidate.add(new TrelloBoard("02", "Board1", new ArrayList<>()));
        listToValidate.add(new TrelloBoard("03", "Board2", new ArrayList<>()));

        //When
        List<TrelloBoard> expectedList = trelloValidator.validateTrelloBoards(listToValidate);

        //Then
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(2, expectedList.size());
        assertEquals(Level.INFO, logsList.get(1).getLevel());
        assertEquals("Boards have been filtered. Current list size: 2", logsList.get(1).getMessage());
    }
}
