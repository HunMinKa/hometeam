package spring.hometeam.service.inf;

import spring.hometeam.domain.entity.Comment;

public interface ICommentService {
    Comment addCommentToTask(int taskId, String content, int userId);
}
