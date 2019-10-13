package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTestSuite {

    @Autowired
    private TaskRepository repository;

    @Autowired DbService dbService;

    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void prepare(){
        task1 = new Task("task1", "testing");
        task2 = new Task("task2", "testing");
        task3 = new Task("task3", "testing");
        task4 = new Task("task4", "testing");
        repository.save(task1);
        repository.save(task2);
        repository.save(task3);
        repository.save(task4);
    }

    @Test
    public void shouldGetAllTasks(){
        //Given
        //When
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertEquals(4, tasks.size());
    }

    @Test
    public void shouldGetTaskById(){
        //Given
        //When
        Optional<Task> expectedTask1 = dbService.getTaskById(task1.getId());
        Optional<Task> expectedTask2 = dbService.getTaskById(task2.getId());
        Optional<Task> expectedTask3 = dbService.getTaskById(task3.getId());
        Optional<Task> expectedTask4 = dbService.getTaskById(task4.getId());
        //Then
        assertTrue(expectedTask1.isPresent());
        assertTrue(expectedTask2.isPresent());
        assertTrue(expectedTask3.isPresent());
        assertTrue(expectedTask4.isPresent());
    }

    @Test
    public void shouldSaveTask(){
        //Given
        //When
        Task newTask = dbService.saveTask(new Task("New Task", "Testing"));
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertEquals(5, tasks.size());
        assertEquals(newTask.getId(), tasks.get(4).getId());
        assertEquals(newTask.getTitle(), tasks.get(4).getTitle());
        assertEquals(newTask.getContent(), tasks.get(4).getContent());
    }

    @Test
    public void shouldDeleteTaskById(){
        //Given
        //When
        dbService.deleteTaskById(task1.getId());
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertEquals(3, tasks.size());
    }

    @After
    public void cleanUp(){
        repository.deleteAll();
    }
}
