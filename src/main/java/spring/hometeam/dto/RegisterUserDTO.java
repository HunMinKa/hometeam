package spring.hometeam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    private String name;
    private String encKey;
    private String csr;
    private String password;
    private String authCode;
}
