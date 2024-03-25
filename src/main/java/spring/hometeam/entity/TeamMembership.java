package spring.hometeam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TeamMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}