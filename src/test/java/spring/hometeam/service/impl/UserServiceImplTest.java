package spring.hometeam.service.impl;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.hometeam.domain.dto.UserDTO;
import spring.hometeam.domain.entity.User;
import spring.hometeam.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        // 모킹된 userRepository를 사용하여 save 메서드를 호출하면 예상된 결과를 반환하도록 설정
        User user = new User();
        user.setId(1);
        user.setName("testuser");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // UserService의 createUser 메서드 호출
        User createdUser = userService.createUser(user);

        // 결과 확인
        assertEquals(1, createdUser.getId());
        assertEquals("testuser", createdUser.getName());

        // userRepository의 save 메서드가 정확히 한 번 호출되었는지 확인
        verify(userRepository, times(1)).save(any(User.class));
    }


}