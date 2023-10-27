package spring.hometeam.domain.entity;

import jdk.jfr.Timestamp;
import lombok.Data;

@Data
public class Comment {
    private int id;
    private String content;
    private int taskId;
    private int userId;
    private Timestamp timestamp;
}
