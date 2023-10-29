package spring.hometeam.repository;

import spring.hometeam.domain.entity.Comment;

import java.util.List;

public interface CommentRepository {

    Comment findById(int commentId);

    List<Comment> findByTaskId(int taskId);

    Comment save(Comment comment);

    boolean removeById(int id);

    void clear();

}
