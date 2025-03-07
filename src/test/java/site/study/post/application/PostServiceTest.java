package site.study.post.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.study.post.application.dto.LikeRequestDto;
import site.study.post.application.dto.UpdatePostRequestDto;
import site.study.post.domain.Post;
import site.study.post.domain.PostPublicationState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class PostServiceTest extends PostApplicationTestTemplate {

    @DisplayName("게시글 저장 테스트")
    @Test
    void givenPostRequestDto_whenCreate_thenReturnPost() {
        // When
        final Post savedPost = postService.createPost(createPostRequestDto);

        // Then
        final Post post = postService.getPost(savedPost.getId());
        assertThat(savedPost).isEqualTo(post);
    }
    
    @DisplayName("게시글 수정 테스트")
    @Test
    void givenCreatePost_whenUpdate_thenReturnUpdatePost() {
        // Given
        final Post savedPost = postService.createPost(createPostRequestDto);
        final UpdatePostRequestDto requestDto = new UpdatePostRequestDto(savedPost.getId(), user.getId(), "update content", PostPublicationState.PUBLIC);

        // When
        final Post updatedPost = postService.updatePost(requestDto);

        // Then
        assertSoftly(softly -> {
            assertThat(savedPost.getId()).isEqualTo(updatedPost.getId());
            assertThat(savedPost.getAuthor()).isEqualTo(updatedPost.getAuthor());
            assertThat(savedPost.getContent()).isEqualTo(updatedPost.getContent());
            assertThat(savedPost.getState()).isEqualTo(updatedPost.getState());
        });
    }
    
    @DisplayName("게시글 좋아요 테스트")
    @Test
    void givenCreatedPostWhenLikedThenReturnPostWithLike() {
        // Given
        final Post savedPost = postService.createPost(createPostRequestDto);

        // When
        final LikeRequestDto requestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(requestDto);
        
        // Then
        assertThat(savedPost.getLikeCount()).isEqualTo(1);
    }

    @DisplayName("좋아요를 두번 눌러도 1만 카운팅 되는지 테스트")
    @Test
    void givenCreatedPostWhenLikedTwiceThenReturnPostWithLike() {
        // Given
        Post savedPost = postService.createPost(createPostRequestDto);

        // When
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // Then
        assertThat(savedPost.getLikeCount()).isEqualTo(1);
    }
    
    @DisplayName("게시글 싫어요 테스트")
    @Test
    void givenCreatedPostWhenUnlikedThenReturnPostWithoutLike() {
        // Given
        Post savedPost = postService.createPost(createPostRequestDto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // When
        postService.unlikePost(likeRequestDto);

        // Then
        assertThat(savedPost.getLikeCount()).isEqualTo(0);
    }

    @DisplayName("싫어요를 눌렀을 때 0이하로 내려가지 않는지 테스트")
    @Test
    void givenCreatedPostWhenUnlikedTwiceThenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(createPostRequestDto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);
        postService.unlikePost(likeRequestDto);

        // then
        assertThat(savedPost.getLikeCount()).isEqualTo(0);
    }
}
