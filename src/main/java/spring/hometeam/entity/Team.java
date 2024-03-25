package spring.hometeam.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Team {

    @Id
    private String teamId;
    private String teamName;
    private String teamCert;
    private Date createDate;
    private Date updateDate;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private Set<TeamMembership> teamMembership  = new HashSet<>();
}
