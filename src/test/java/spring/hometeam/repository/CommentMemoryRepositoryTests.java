package spring.hometeam.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.hometeam.domain.entity.Comment;
import spring.hometeam.repository.CommentRepository;
import spring.hometeam.repository.impl.CommentMemoryRepository;
import spring.hometeam.repository.inf.ICommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CommentMemoryRepositoryTests {

    private final CommentRepository commentRepository = new CommentMemoryRepository();

    @BeforeEach
    void setup() {
        Comment comment1 = new Comment();
        comment1.setUserId(0);
        comment1.setTaskId(0);
        comment1.setContent("hello");
        this.commentRepository.save(comment1);
        Comment comment2 = new Comment();
        comment2.setUserId(1);
        comment2.setTaskId(0);
        comment2.setContent("hi, there");
        this.commentRepository.save(comment2);
    }

    @AfterEach
    void clear() {
        this.commentRepository.removeById(0);
        this.commentRepository.removeById(1);
    }

    @Test
    void findById() {
        Comment comment1 = this.commentRepository.findById(0);
        assertThat(comment1.getUserId()).isEqualTo(0);
        assertThat(comment1.getTaskId()).isEqualTo(0);
        assertThat(comment1.getContent()).isEqualTo("hello");

        Comment comment2 = this.commentRepository.findById(1);
        assertThat(comment2.getUserId()).isEqualTo(1);
        assertThat(comment2.getTaskId()).isEqualTo(0);
        assertThat(comment2.getContent()).isEqualTo("hi, there");
    }

    @Test
    void findByTaskId() {
        List<Comment> comments = this.commentRepository.findByTaskId(0);
        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    void save() {
        Comment comment = new Comment();
        comment.setUserId(2);
        comment.setTaskId(1);
        comment.setContent("this is a new comment");
        Comment savedComment = this.commentRepository.save(comment);
        assertThat(savedComment.getId()).isEqualTo(2);
        assertThat(savedComment.getUserId()).isEqualTo(2);
        assertThat(savedComment.getTaskId()).isEqualTo(1);
        assertThat(savedComment.getContent()).isEqualTo("this is a new comment");
    }

    @Test
    void delete() {
        this.commentRepository.removeById(1);
        List<Comment> comments = this.commentRepository.findByTaskId(0);
        assertThat(comments.size()).isEqualTo(1);
    }

}
