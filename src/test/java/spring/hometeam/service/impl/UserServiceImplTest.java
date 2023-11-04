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

    // 테스트 대상 클래스의 인스턴스를 생성하고, @Mock 어노테이션으로 생성된 모의 객체를 그 안에 주입합니다.
    @InjectMocks
    private UserServiceImpl userService;

    //주어진 의존성에 대한 모의 구현을 생성하는 데 사용되는 어노테이션
    @Mock
    private UserRepository userRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
       // 이 메소드는 MockitoAnnotations.initMocks(this);를 사용하여 모의 객체를 초기화합니다. 모의 객체를 userService에 주입하기 위해 준비.
    }

    @Test
    public void testCreateUser() {
        /*Arrange-Act-Assert (AAA) 패턴
           Arrange: 테스트를 설정합니다. 사용자 객체를 생성
           Act: 테스트 대상 메소드를 호출
           Assert: 결과를 기대값과 비교합니다
         */


        // Arrange: 모킹된 userRepository를 사용하여 save 메서드를 호출하면 예상된 결과를 반환하도록 설정
        User user = new User();
        user.setId(1);
        user.setName("testuser");

        when(userRepository.save(any(User.class))).thenReturn(user);
        // 단위 테스트에서 UserRepository의 save() 메서드가 호출될 때 실제 데이터베이스에 접근하는 대신에, 미리 정의한 User 객체를 반환하도록 설정하여 UserRepository와 상호 작용하는 코드를 테스트할 때 일관된 결과를 얻을 수 있게 하는 것


        // Act: UserService의 createUser 메서드 호출
        User createdUser = userService.createUser(user);

        // Assert: 결과 확인
        assertEquals(1, createdUser.getId());
        assertEquals("testuser", createdUser.getName());

        // userRepository의 save 메서드가 정확히 한 번 호출되었는지 확인
        verify(userRepository, times(1)).save(any(User.class));
    }


}