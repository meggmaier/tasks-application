package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private MailCreatorService mailCreatorService;

    @Test
    public void shouldFetchEmptyBoard(){
        //Given
        //When
        when(trelloService.fetchTrelloBoards()).thenReturn(new ArrayList<>());
        List<TrelloBoardDto> emptyTrelloBoard = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(0, emptyTrelloBoard.size());
    }

    @Test
    public void shouldFetchTrelloBoards(){
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "test board", new ArrayList<>()));
        trelloBoardDtoList.add(new TrelloBoardDto("2", "test board2", new ArrayList<>()));
        //When
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtoList);
        List<TrelloBoardDto> expectedTrelloBoardsList = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(2, expectedTrelloBoardsList.size());
        assertEquals("test board", expectedTrelloBoardsList.get(0).getName());
        assertEquals("test board2", expectedTrelloBoardsList.get(1).getName());
    }

    @Test
    public void shouldSentCreatedCardMail() throws IOException {
        //Given
        String subject = "Tasks: New Trello Card";
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "listId");
        URL url = Resources.getResource("trello-card-mail.txt");
        String text = Resources.toString(url, Charsets.UTF_8);
        ArgumentCaptor<Mail> argumentCaptor = ArgumentCaptor.forClass(Mail.class);
        Mail mail = new Mail("test@test.com", subject, text, "");

        //When
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(new CreatedTrelloCardDto());
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        when(mailCreatorService.buildTrelloCardEmail(anyString())).thenReturn(text);

        trelloService.createTrelloCard(trelloCardDto);

        //Then
        verify(simpleEmailService, times(1)).send(any(Mail.class));
        verify(simpleEmailService).send(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getMessage(), mail.getMessage());
        assertEquals(argumentCaptor.getValue().getMailTo(), mail.getMailTo());
        assertEquals(argumentCaptor.getValue().getSubject(), mail.getSubject());
        assertEquals(argumentCaptor.getValue().getToCc(), mail.getToCc());
    }
}
