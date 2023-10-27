package spring.hometeam.domain.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private int teamId;
    private Integer parentId;
    private int completionRate;
    private Date dueDate;
    private String priority;
}
