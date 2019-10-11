package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoard(){
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "board1", new ArrayList<>());
        trelloBoardDtoList.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoard(trelloBoardDtoList);
        //Then
        assertEquals(1, trelloBoardList.size());
        assertEquals(trelloBoardDto.getId(), trelloBoardList.get(0).getId());
        assertEquals(trelloBoardDto.getName(), trelloBoardList.get(0).getName());
    }

    @Test
    public void testMapToBoardsDto(){
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "board2", new ArrayList<>());
        TrelloBoard trelloBoard3 = new TrelloBoard("3", "board3", new ArrayList<>());
        trelloBoardList.add(trelloBoard2);
        trelloBoardList.add(trelloBoard3);

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        //Then
        assertEquals(2, trelloBoardDtoList.size());
        assertEquals(trelloBoard2.getId(), trelloBoardDtoList.get(0).getId());
        assertEquals(trelloBoard2.getName(), trelloBoardDtoList.get(0).getName());
        assertEquals(trelloBoard3.getId(), trelloBoardDtoList.get(1).getId());
        assertEquals(trelloBoard3.getName(), trelloBoardDtoList.get(1).getName());
    }

    @Test
    public void testMapToList(){
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        TrelloListDto trelloList1 = new TrelloListDto("1", "trello list DTO1", true);
        TrelloListDto trelloList2 = new TrelloListDto("2", "trello list DTO2", false);
        trelloListDto.add(trelloList1);
        trelloListDto.add(trelloList2);
        //When
        List<TrelloList> expectedTrelloList = trelloMapper.mapToList(trelloListDto);
        //Then
        assertEquals(2, expectedTrelloList.size());
        assertEquals(trelloList1.getId(), expectedTrelloList.get(0).getId());
        assertEquals(trelloList2.getId(), expectedTrelloList.get(1).getId());
        assertEquals(trelloList1.getName(), expectedTrelloList.get(0).getName());
        assertEquals(trelloList2.getName(), expectedTrelloList.get(1).getName());
        assertTrue(expectedTrelloList.get(0).isClosed());
        assertFalse(expectedTrelloList.get(1).isClosed());
    }
    @Test
    public void testMapToListDto(){
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("1", "trello list 1", true);
        TrelloList trelloList2 = new TrelloList("2", "trello list 2", false);
        trelloList.add(trelloList1);
        trelloList.add(trelloList2);
        //When
        List<TrelloListDto> expectedTrelloListDto = trelloMapper.mapToListDto(trelloList);
        //Then
        assertEquals(2, expectedTrelloListDto.size());
        assertEquals(trelloList1.getId(), expectedTrelloListDto.get(0).getId());
        assertEquals(trelloList2.getId(), expectedTrelloListDto.get(1).getId());
        assertEquals(trelloList1.getName(), expectedTrelloListDto.get(0).getName());
        assertEquals(trelloList2.getName(), expectedTrelloListDto.get(1).getName());
        assertTrue(expectedTrelloListDto.get(0).isClosed());
        assertFalse(expectedTrelloListDto.get(1).isClosed());
    }
    @Test
    public void testMapToCardDto(){
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "list ID 001");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }
    @Test
    public void testMapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "test", "list ID 002");
        //When
        TrelloCard expectedTrelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getName(), expectedTrelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), expectedTrelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), expectedTrelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), expectedTrelloCard.getListId());
    }

}
