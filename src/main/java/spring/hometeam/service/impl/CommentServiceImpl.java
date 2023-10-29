package spring.hometeam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hometeam.domain.entity.Comment;
import spring.hometeam.repository.CommentRepository;
import spring.hometeam.service.inf.ICommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsOfTask(int taskId) {
        return this.commentRepository.findByTaskId(taskId);
    }

    @Override
    public Comment addCommentToTask(int taskId, String content, int userId) {
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setParentId(-1);
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment addCommentToComment(int parentId, String content, int userId) {
        Comment parentComment = this.commentRepository.findById(parentId);
        Comment comment = new Comment();
        comment.setTaskId(parentComment.getTaskId());
        comment.setParentId(parentComment.getId());
        comment.setContent(content);
        comment.setUserId(userId);
        return this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        this.commentRepository.delete(commentId);
    }

}
