package spring.hometeam.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.hometeam.domain.entity.Comment;
import spring.hometeam.repository.CommentMemoryRepository;
import spring.hometeam.repository.CommentRepository;
import spring.hometeam.service.inf.ICommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CommentServiceImplTests {

    private final CommentRepository commentRepository = new CommentMemoryRepository();
    private final ICommentService commentService = new CommentServiceImpl(commentRepository);

    @BeforeEach
    void setup() {
    }

    @AfterEach
    void clear() {
        this.commentRepository.clear();
    }

    @Test
    void addCommentToTask() {
        Comment comment1 = this.commentService.addCommentToTask(0, "comment 1", 0);
        assertThat(comment1.getTaskId()).isEqualTo(0);
        assertThat(comment1.getUserId()).isEqualTo(0);
        assertThat(comment1.getParentId()).isEqualTo(-1);
        assertThat(comment1.getContent()).isEqualTo("comment 1");

        Comment comment2 = this.commentService.addCommentToTask(0, "comment 2", 1);
        assertThat(comment2.getTaskId()).isEqualTo(0);
        assertThat(comment2.getUserId()).isEqualTo(1);
        assertThat(comment2.getParentId()).isEqualTo(-1);
        assertThat(comment2.getContent()).isEqualTo("comment 2");
    }

    @Test
    void addCommentToComment() {
        Comment parentComment = this.commentService.addCommentToTask(0, "parent", 0);
        Comment childComment = this.commentService.addCommentToComment(parentComment.getId(), "child", 1);
        assertThat(childComment.getParentId()).isEqualTo(parentComment.getId());
    }

    @Test
    void getCommentsOfTask() {
        Comment comment1 = this.commentService.addCommentToTask(0, "comment 1", 0);
        Comment comment2 = this.commentService.addCommentToTask(0, "comment 2", 1);
        Comment comment3 = this.commentService.addCommentToTask(0, "comment 3", 0);
        List<Comment> comments = this.commentService.getCommentsOfTask(0);
        assertThat(comments).contains(comment1, comment2, comment3);
    }

    @Test
    void deleteComment() {
        Comment comment = this.commentService.addCommentToTask(0, "comment", 0);
        this.commentService.deleteComment(comment.getId());
        List<Comment> comments = this.commentService.getCommentsOfTask(0);
        assertThat(comments.size()).isEqualTo(0);
    }

}
