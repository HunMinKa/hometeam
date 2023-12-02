package spring.hometeam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import spring.hometeam.entity.Task;
import spring.hometeam.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(); // Assume Task has proper fields and methods
        when(taskRepository.save(task)).thenReturn(task);

        Task created = taskService.createTask(task);

        assertEquals(task, created);
        verify(taskRepository).save(task);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> found = taskService.getAllTasks();

        assertEquals(2, found.size());
        verify(taskRepository).findAll();
    }

    @Test
    public void testGetTaskById() {
        int taskId = 1;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Optional<Task> found = taskService.getTaskById(taskId);

        assertEquals(task, found.orElse(null));
        verify(taskRepository).findById(taskId);
    }
}