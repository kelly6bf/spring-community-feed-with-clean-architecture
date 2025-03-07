package site.study.post.application;

import site.study.post.application.dto.CreatePostRequestDto;
import site.study.post.application.dto.LikeRequestDto;
import site.study.post.application.dto.UpdatePostRequestDto;
import site.study.post.application.port.LikeRepository;
import site.study.post.application.port.PostRepository;
import site.study.post.domain.Post;
import site.study.user.application.UserService;
import site.study.user.domain.User;

import java.util.NoSuchElementException;

public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    public PostService(
        final PostRepository postRepository,
        final LikeRepository likeRepository,
        final UserService userService
    ) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userService = userService;
    }

    public Post getPost(final Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);
    }

    public Post createPost(final CreatePostRequestDto requestDto) {
        final User author = userService.getUser(requestDto.userId());
        final Post post = Post.createPost(author, requestDto.content(), requestDto.state());
        return postRepository.save(post);
    }

    public Post updatePost(final UpdatePostRequestDto requestDto) {
        final User user = userService.getUser(requestDto.userId());
        final Post post = getPost(requestDto.postId());

        post.update(user, requestDto.content(), requestDto.state());
        return postRepository.save(post);
    }

    public void likePost(final LikeRequestDto requestDto) {
        final Post post = getPost(requestDto.targetId());
        final User user = userService.getUser(requestDto.userId());

        if (likeRepository.checkLike(post, user)) {
            return;
        }

        post.like(user);
        likeRepository.like(post, user);
    }

    public void unlikePost(final LikeRequestDto requestDto) {
        final Post post = getPost(requestDto.targetId());
        final User user = userService.getUser(requestDto.userId());

        if (!likeRepository.checkLike(post, user)) {
            return;
        }

        post.unlike(user);
        likeRepository.unlike(post, user);
    }
}
