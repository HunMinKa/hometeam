package spring.hometeam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hometeam.domain.entity.Task;
import spring.hometeam.repository.TaskRepository;
import spring.hometeam.service.inf.ITaskService;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task createSubTask(int parentId, Task subTask) {
        subTask.parentId = parentId;
        return taskRepository.save(subTask);
    }

    // JPA 강의 시청후 개발 예쩡
    @Override
    public Task setPriority(int taskId, String priority) {
        return null;
    }

    @Override
    public Task assignLabelToTask(int taskId, int labelId) {
        return null;
    }

    @Override
    public List<Task> filterTasksByPriority(String priority) {
        return null;
    }

    @Override
    public List<Task> searchTasksByLabel(int labelId) {
        return null;
    }
}
