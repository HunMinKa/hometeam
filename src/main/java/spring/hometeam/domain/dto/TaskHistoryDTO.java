package spring.hometeam.domain.dto;

import jdk.jfr.Timestamp;
import lombok.Data;

@Data
public class TaskHistoryDTO {
    private int id;
    private String action;
    private int taskId;
    private Timestamp timestamp;
}
