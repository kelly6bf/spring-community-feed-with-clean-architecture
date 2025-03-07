package site.study.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void givenNameAndProfileImage_whenCreated_thenThrowNothing() {
        // Given
        final String name = "abcd";
        final String profileImageUrl = "";

        // When & Then
        assertDoesNotThrow(() -> new UserInfo(name , profileImageUrl));
    }

    @DisplayName("객체 생성 예외 테스트")
    @Test
    void givenBlankNameAndProfileImage_whenCreated_thenThrowError() {
        // Given
        final String name = "";
        final String profileImageUrl = "";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> new UserInfo(name , profileImageUrl));
    }
}
