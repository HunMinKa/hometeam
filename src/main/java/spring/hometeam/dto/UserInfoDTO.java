package spring.hometeam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private int id;
    private String name;
    private String email;
    private String pubKey;
    private String pushId;
}
