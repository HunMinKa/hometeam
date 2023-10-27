package spring.hometeam.service.inf;

import spring.hometeam.domain.entity.TaskHistory;

import java.util.List;

public interface ITaskHistoryService {
    List<TaskHistory> getTaskHistory(int taskId);
}
