package spring.hometeam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TeamMembership {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}