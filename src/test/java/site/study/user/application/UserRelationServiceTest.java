package site.study.user.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.study.fake.FakeObjectFactory;
import site.study.user.application.dto.CreateUserRequestDto;
import site.study.user.application.dto.FollowUserRequestDto;
import site.study.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class UserRelationServiceTest {

    private final UserService userService = FakeObjectFactory.userService();
    private final UserRelationService userRelationService = FakeObjectFactory.userRelationService();

    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    @BeforeEach
    void init() {
        this.user1 = userService.createUser(new CreateUserRequestDto("test1", ""));
        this.user2 = userService.createUser(new CreateUserRequestDto("test2", ""));

        this.requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
    }

    @DisplayName("팔로우 정상 플로우 테스트")
    @Test
    void givenCreateTwoUser_whenFollow_thenUserFollowSaved() {

        // When
        userRelationService.follow(requestDto);
        
        // Then
        assertSoftly(softly -> {
            assertThat(user1.getFollowingCount()).isEqualTo(1);
            assertThat(user2.getFollowerCount()).isEqualTo(1);
        });
    }

    @DisplayName("이미 팔로우한 유저를 또 팔로우하는 경우 예외 발생 테스트")
    @Test
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError() {
        // Given
        userRelationService.follow(requestDto);

        // When & Then
        assertThatThrownBy(() -> userRelationService.follow(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @DisplayName("자기 자신을 팔로우하면 예외를 발생시킨다.")
    @Test
    void givenCreateOneUser_whenFollow_thenUserThrowError() {
        // Given
        final FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // When && Then
        assertThatThrownBy(() -> userRelationService.follow(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("언팔로우 정상 플로우 테스트")
    @Test
    void givenCreateTwoUserFollow_whenUnFollow_thenUserUnFollowSaved() {
        // Given
        userRelationService.follow(requestDto);

        // When
        userRelationService.unfollow(requestDto);

        // Then
        assertSoftly(softly -> {
            assertThat(user1.getFollowingCount()).isEqualTo(0);
            assertThat(user2.getFollowerCount()).isEqualTo(0);
        });
    }

    @DisplayName("팔로우 관계가 아닌 사용자들을 언팔로우 하면 예외를 발생시킨다.")
    @Test
    void givenCreateTwoUser_whenUnFollow_thenUserThrowError() {
        // When & Then
        assertThatThrownBy(() -> userRelationService.unfollow(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자기 자신을 언팔로우하면 예외를 발생시킨다.")
    @Test
    void givenCreateOneUser_whenUnFollow_thenUserThrowError() {
        // Given
        final FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user1.getId());

        // When && Then
        assertThatThrownBy(() -> userRelationService.unfollow(requestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
