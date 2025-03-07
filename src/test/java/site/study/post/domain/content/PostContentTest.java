package site.study.post.domain.content;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PostContentTest {

    @DisplayName("게시글 컨텐츠 생성 테스트")
    @Test
    void givenContentLengthIsOkWhenCreatePostContentThenReturnTextContext() {
        // given
        String contentText = "this is a test content";

        // when
        PostContent content = new PostContent(contentText);

        // then
        assertEquals(contentText, content.getContentText());
    }

    @DisplayName("게시글 컨텐츠가 제약 조건을 초과해서 입력된 경우 예외 테스트")
    @Test
    void givenContentLengthIsOverLimitCreatePostContentThenThrowError() {
        // given
        String content = "a".repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @DisplayName("게시글 컨텐츠가 한글을 포함해 제약 조건을 초과해서 입력된 경우 예외 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"뷁", "닭", "굵"})
    void givenContentLengthIsOverLimitAndKoreanCreatePostContentThenThrowError(String koreanContent) {
        // given
        String content = koreanContent.repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @DisplayName("게시글 컨텐츠가 제약 조건 미만으로 입력된 경우 예외 테스트")
    @Test
    void givenContentLengthIsUnderLimitCreatePostContentThenThrowError() {
        // given
        String content = "abcd";

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @DisplayName("게시글 컨텐츠로 null 혹은 빈값이 입력될 경우 예외 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsEmptyAndNullLimitCreatePostContentThenThrowError(String content) {
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    /**
     * 여기서부터는 깃헙 코파일럿이 작성한 테스트
     */

    @Test
    void givenContentLengthIsOkWhenUpdateContentThenNotThrowError() {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when
        String newContent = "this is an other test content";
        postContent.updateContent(newContent);

        // then
        assertEquals(newContent, postContent.getContentText());
        assertTrue(postContent.isEdited());
    }

    @Test
    void givenContentLengthIsOverLimitWhenUpdateContentThenThrowError() {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        String overLimitContent = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(overLimitContent));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsOverLimitAndKoreanWhenUpdateContentThenThrowError(String nullOrEmptyContent) {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(nullOrEmptyContent));
    }
}
