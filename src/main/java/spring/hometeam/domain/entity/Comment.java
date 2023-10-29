package spring.hometeam.domain.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private int id;
    private int parentId;
    private String content;
    private int taskId;
    private int userId;
    private Timestamp timestamp;
}
