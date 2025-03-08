package site.study.post.domain.content;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommentContentTest {

    @DisplayName("게시글 댓글 생성 테스트")
    @Test
    void givenContentLengthIsOkWhenCreatePostContentThenNotThrowError() {
        // given
        String content = "this is a test content";

        // when, then
        assertDoesNotThrow(() -> new CommentContent(content));
    }

    @DisplayName("게시글 댓글 생성시 입력값 길이 초과 검증 테스트")
    @Test
    void givenContentLengthIsOverLimitCreatePostContentThenThrowError() {
        // given
        String content = "a".repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @DisplayName("게시글 댓글 생성시 한글 입력값 길이 초과 검증 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"뷁", "닭", "굵"})
    void givenContentLengthIsOverLimitAndKoreanCreatePostContentThenThrowError(String koreanContent) {
        // given
        String content = koreanContent.repeat(101);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(content));
    }

    @Test
    void givenContentLengthIsUnderLimitCreatePostContentThenNotThrowError() {
        // given
        String content = "abcd";

        // when, then
        assertDoesNotThrow(() -> new CommentContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsEmptyLimitCreatePostContentThenThrowError(String source) {
        assertThrows(IllegalArgumentException.class, () -> new CommentContent(source));
    }
}
