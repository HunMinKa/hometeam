package spring.hometeam.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int teamId;
    public Integer parentId;
    private int completionRate;
    private Date dueDate;
    private String priority;

    // 생성자, getters, setters 및 필요한 메서드들
}