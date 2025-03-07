package site.study.user.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.study.fake.FakeObjectFactory;
import site.study.user.application.dto.CreateUserRequestDto;
import site.study.user.domain.User;
import site.study.user.domain.UserInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class UserServiceTest {

    private final UserService userSErvice = FakeObjectFactory.userService();

    @DisplayName("유저 생성 테스트")
    @Test
    void givenUserInfoDto_whenCreateUser_thenCanFindUser() {
        // Given
        final CreateUserRequestDto requestDto = new CreateUserRequestDto("test", "");

        // When
        final User savedUser = userSErvice.createUser(requestDto);

        // Then
        final User foundUser = userSErvice.getUser(savedUser.getId());
        final UserInfo userInfo = foundUser.getInfo();
        assertSoftly(softly -> {
            assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
            assertThat(userInfo.getName()).isEqualTo("test");
        });
    }
}
