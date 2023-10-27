package spring.hometeam.domain.dto;

import jdk.jfr.Timestamp;
import lombok.Data;

@Data
public class CommentDTO {
    private int id;
    private String content;
    private int taskId;
    private int userId;
    private Timestamp timestamp;
}
