package spring.hometeam.repository;

import org.springframework.stereotype.Repository;
import spring.hometeam.domain.entity.Comment;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentMemoryRepository implements CommentRepository {

    private static final Map<Integer, Comment> map = new HashMap<>();

    @Override
    public Comment findById(int commentId) {
        return map.get(commentId);
    }

    @Override
    public List<Comment> findByTaskId(int taskId) {
        List<Comment> comments = new ArrayList<>();
        map.forEach((k,v)->{
            if (v.getTaskId() == taskId) {
                comments.add(v);
            }
        });
        return comments;
    }

    @Override
    public Comment save(Comment comment) {
        comment.setId(map.size());
        comment.setTimestamp(Timestamp.from(Instant.now()));
        map.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public boolean removeById(int id) {
        boolean itemExists = map.containsKey(id);
        if (itemExists) {
            map.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
