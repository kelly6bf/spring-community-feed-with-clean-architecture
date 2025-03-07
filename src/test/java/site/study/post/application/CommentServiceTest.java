package site.study.post.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.study.post.application.dto.LikeRequestDto;
import site.study.post.application.dto.UpdateCommentRequestDto;
import site.study.post.domain.comment.Comment;
import site.study.post.domain.content.Content;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentServiceTest extends PostApplicationTestTemplate {

    @DisplayName("게시글 댓글 생성 테스트")
    @Test
    void givenCreateCommentRequestDtoWhenCreateCommentThenReturnComment() {
        // when
        final Comment comment = commentService.createComment(createCommentRequestDto);

        // then
        final Content content = comment.getContent();
        assertThat(content.getContentText()).isEqualTo(createCommentRequestDto.content());
    }

    @DisplayName("게시글 댓글 수정 테스트")
    @Test
    void givenCreateCommentWhenUpdateCommentThenReturnUpdatedComment() {
        // given
        final Comment comment = commentService.createComment(createCommentRequestDto);
        final String updatedCommentContent = "this is updated comment";
        final UpdateCommentRequestDto updateCommentRequestDto = new UpdateCommentRequestDto(comment.getId(), user.getId(), updatedCommentContent);

        // when
        final Comment updatedComment = commentService.updateComment(updateCommentRequestDto);

        // then
        final Content content = updatedComment.getContent();
        assertThat(content.getContentText()).isEqualTo(updatedCommentContent);
    }

    @DisplayName("게시글 댓글 좋아요 테스트")
    @Test
    void givenCommentWhenLikeCommentThenReturnCommentWithLike() {
        // given
        final Comment comment = commentService.createComment(createCommentRequestDto);
        final LikeRequestDto likeRequestDto = new LikeRequestDto(comment.getId(), otherUser.getId());

        // when
        commentService.likeComment(likeRequestDto);

        // then
        assertThat(comment.getLikeCount()).isEqualTo(1);
    }

    @DisplayName("게시글 댓글 싫어요 테스트")
    @Test
    void givenCommentWhenUnlikeCommentThenReturnCommentWithoutLike() {
        // given
        final Comment comment = commentService.createComment(createCommentRequestDto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(comment.getId(), otherUser.getId());
        commentService.likeComment(likeRequestDto);

        // when
        commentService.unlikeComment(likeRequestDto);

        // then
        assertThat(comment.getLikeCount()).isEqualTo(0);
    }

    @DisplayName("본인 게시글 댓글의 좋아요를 누룰 경우 예외 발생 테스트")
    @Test
    void givenCommentWhenLikeSelfThenThrowException() {
        // given
        final Comment comment = commentService.createComment(createCommentRequestDto);
        final LikeRequestDto likeRequestDto = new LikeRequestDto(comment.getId(), user.getId());

        // when, then
        assertThatThrownBy(() -> commentService.likeComment(likeRequestDto))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
