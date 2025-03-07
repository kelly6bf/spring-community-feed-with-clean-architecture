package site.study.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final UserInfo userInfo = new UserInfo("test", "");
    private final User user1 = new User(1L, userInfo);;
    private final User user2 = new User(2L, userInfo);

    @DisplayName("hash & equals 동등성 테스트")
    @Test
    void givenCreateSameIdUserWhenEqualSameIdThenReturnTrue() {
        // given
        UserInfo testInfo = new UserInfo("test1", "1");
        User oneUser = new User(1L, testInfo);

        // when, then
        assertEquals(oneUser, user1);
    }

    @DisplayName("팔로우 테스트")
    @Test
    void givenUser1WhenFollowUser2ThenUser1IncreaseFollowingCountUser2IncreaseFollowerCount() {
        // given, when
        user1.follow(user2);

        // then
        assertEquals(1, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(1, user2.getFollowerCount());
    }

    @DisplayName("언팔로우 테스트")
    @Test
    void givenUser1FollowUser2WhenUser1UnfollowUser2ThenReturnZero() {
        // given
        user1.follow(user2);

        //when
        user1.unfollow(user2);

        // then
        assertEquals(0, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(0, user2.getFollowerCount());
    }

    @DisplayName("unfollow시 팔로잉 & 팔로워 수가 0 이하로 감소되지 않는지 테스트")
    @Test
    void whenUser1UnfollowUser2ThenAllCountZero() {
        //when
        user1.unfollow(user2);

        // then
        assertEquals(0, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(0, user2.getFollowerCount());
    }

    @DisplayName("본인을 팔로우시 예외 발생 테스트")
    @Test
    void givenOneUserWhenFollowSameUserThenThrowError() {
        assertThrows(IllegalArgumentException.class, () -> user1.follow(user1));
    }

    @DisplayName("본인을 언팔로우시 예외 발생 테스트")
    @Test
    void givenOneUserWhenUnfollowSameUserThenThrowError() {
        assertThrows(IllegalArgumentException.class, () -> user1.unfollow(user1));
    }
}
