package spring.hometeam.service.inf;

import spring.hometeam.domain.entity.Comment;

import java.util.List;

public interface ICommentService {

    List<Comment> getCommentsOfTask(int taskId);

    Comment addCommentToTask(int taskId, String content, int userId);

    Comment addCommentToComment(int parentId, String content, int userId);

    void deleteComment(int commentId);

}
