package spring.hometeam.service.inf;

import spring.hometeam.domain.entity.Task;

import java.util.List;

public interface ITaskService {
    Task createTask(Task task);
    Task createSubTask(int parentId, Task subTask);
    Task setPriority(int taskId, String priority);
    Task assignLabelToTask(int taskId, int labelId);
    List<Task> filterTasksByPriority(String priority);
    List<Task> searchTasksByLabel(int labelId);
}
