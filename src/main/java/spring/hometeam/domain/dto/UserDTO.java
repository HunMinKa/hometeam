package spring.hometeam.domain.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String email;
    private String phone;
    private String name;
    private String password;
}
