package site.study.post.application;

import site.study.fake.FakeObjectFactory;
import site.study.post.application.dto.CreateCommentRequestDto;
import site.study.post.application.dto.CreatePostRequestDto;
import site.study.post.domain.Post;
import site.study.post.domain.PostPublicationState;
import site.study.user.application.UserService;
import site.study.user.application.dto.CreateUserRequestDto;
import site.study.user.domain.User;

public class PostApplicationTestTemplate {

    final UserService userService = FakeObjectFactory.userService();
    final PostService postService = FakeObjectFactory.postService();
    final CommentService commentService = FakeObjectFactory.commentService();

    final User user = userService.createUser(new CreateUserRequestDto("user1", null));
    final User otherUser = userService.createUser(new CreateUserRequestDto("user2", null));

    final CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(user.getId(), "this is test content", PostPublicationState.PUBLIC);
    final Post post = postService.createPost(createPostRequestDto);

    final CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(post.getId(), user.getId(), "this is test comment");
}
