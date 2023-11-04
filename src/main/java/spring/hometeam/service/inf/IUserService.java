package spring.hometeam.service.inf;

import spring.hometeam.domain.dto.UserDTO;
import spring.hometeam.domain.entity.User;

//작업을 분류하거나 특정 키워드로 빠르게 검색할 수 있도록 라벨이나 태그를 부여하는 기능.
public interface IUserService {
    User createUser(User user);
/*    User getUserByEmail(String email);*/
    User updateUserDetails(int userId, String email, String name, String phone);
}
