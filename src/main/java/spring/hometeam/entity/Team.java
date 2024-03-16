package spring.hometeam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Team {

    @Id
    private String teamId;
    private String teamKey;
    private String teamName;
    private Date createDate;
    private Date updateDate;

}
