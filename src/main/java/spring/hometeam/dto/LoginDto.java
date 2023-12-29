package spring.hometeam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String email;
    private String password;
    private String signature;
    private String pubKey;
}
