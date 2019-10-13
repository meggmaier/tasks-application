package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask(){
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "testing");
        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(taskDto.getId(), mappedTask.getId());
        assertEquals(taskDto.getTitle(), mappedTask.getTitle());
        assertEquals(taskDto.getContent(), mappedTask.getContent());
    }

    @Test
    public void testMapToTaskDto(){
        //Given
        Task task = new Task(1L, "Test", "testing");
        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(task.getId(), mappedTaskDto.getId());
        assertEquals(task.getTitle(), mappedTaskDto.getTitle());
        assertEquals(task.getContent(), mappedTaskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList(){
        //Given
        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task(1L, "Task1", "testing");
        Task task2 = new Task(2L, "Task2", "testing");
        Task task3 = new Task(3L, "Task3", "testing");
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        //When
        List<TaskDto> mappedTaskList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        for(int i=0; i<taskList.size(); i++) {
            assertEquals(taskList.get(i).getId(), mappedTaskList.get(i).getId());
            assertEquals(taskList.get(i).getTitle(), mappedTaskList.get(i).getTitle());
            assertEquals(taskList.get(i).getContent(), mappedTaskList.get(i).getContent());
        }
    }
}
