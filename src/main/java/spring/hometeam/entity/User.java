package spring.hometeam.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pubKey;
    private String encKey;
    private String email;
    private String name;
    private String password;
    private String cert;
}