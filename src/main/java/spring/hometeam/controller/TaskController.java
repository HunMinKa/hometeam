package spring.hometeam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.hometeam.entity.Task;
import spring.hometeam.service.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 작업 등록 API 예제
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // 모든 작업 조회 API 예제
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // 특정 작업 조회 API 예제
    @GetMapping("/{taskId}")
    public Optional<Task> getTaskById(@PathVariable int taskId) {
        return taskService.getTaskById(taskId);
    }
}