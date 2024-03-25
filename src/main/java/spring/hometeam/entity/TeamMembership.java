package spring.hometeam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TeamMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "teamId")
    private Team team;
}