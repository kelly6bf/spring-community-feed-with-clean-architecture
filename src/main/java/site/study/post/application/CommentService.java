package site.study.post.application;

import site.study.post.application.dto.CreateCommentRequestDto;
import site.study.post.application.dto.LikeRequestDto;
import site.study.post.application.dto.UpdateCommentRequestDto;
import site.study.post.application.port.CommentRepository;
import site.study.post.application.port.LikeRepository;
import site.study.post.domain.Post;
import site.study.post.domain.comment.Comment;
import site.study.user.application.UserService;
import site.study.user.domain.User;

import java.util.NoSuchElementException;

public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(
        final CommentRepository commentRepository,
        final LikeRepository likeRepository,
        final UserService userService,
        final PostService postService
    ) {
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);
    }

    public Comment createComment(final CreateCommentRequestDto requestDto) {
        final Post post = postService.getPost(requestDto.postId());
        final User user = userService.getUser(requestDto.userId());

        final Comment comment = Comment.createComment(post, user, requestDto.content());
        return commentRepository.save(comment);
    }

    public Comment updateComment(final UpdateCommentRequestDto requestDto) {
        final Comment comment = getComment(requestDto.commentId());
        final User user = userService.getUser(requestDto.userId());

        comment.updateComment(user, requestDto.content());
        return commentRepository.save(comment);
    }

    public void likeComment(final LikeRequestDto requestDto) {
        final Comment comment = getComment(requestDto.targetId());
        final User user = userService.getUser(requestDto.userId());

        if (likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        likeRepository.like(comment, user);
    }

    public void unlikeComment(final LikeRequestDto requestDto) {
        final Comment comment = getComment(requestDto.targetId());
        final User user = userService.getUser(requestDto.userId());

        if (!likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.unlike(user);
        likeRepository.unlike(comment, user);
    }
}
