package site.study.fake;

import site.study.post.application.CommentService;
import site.study.post.application.PostService;
import site.study.post.application.port.CommentRepository;
import site.study.post.application.port.LikeRepository;
import site.study.post.application.port.PostRepository;
import site.study.post.infrastructure.repository.FakeCommentRepository;
import site.study.post.infrastructure.repository.FakeLikeRepository;
import site.study.post.infrastructure.repository.FakePostRepository;
import site.study.user.application.UserRelationService;
import site.study.user.application.UserService;
import site.study.user.application.port.UserRelationRepository;
import site.study.user.application.port.UserRepository;
import site.study.user.infrastructure.repository.FakeUserRelationRepository;
import site.study.user.infrastructure.repository.FakeUserRepository;

public class FakeObjectFactory {

    private static final UserRepository fakeUserRepository = new FakeUserRepository();
    private static final UserRelationRepository fakeUserRelationRepository = new FakeUserRelationRepository();
    private static final PostRepository fakePostRepository = new FakePostRepository();
    private static final CommentRepository fakeCommentRepository = new FakeCommentRepository();
    private static final LikeRepository fakeLikeRepository = new FakeLikeRepository();

    private static final UserService userService = new UserService(fakeUserRepository);
    private static final UserRelationService userRelationService = new UserRelationService(fakeUserRelationRepository, userService);
    private static final PostService postService = new PostService(fakePostRepository, fakeLikeRepository, userService);
    private static final CommentService commentService = new CommentService(fakeCommentRepository, fakeLikeRepository, userService, postService);

    private FakeObjectFactory() {}

    public static UserService userService() {
        return userService;
    }

    public static UserRelationService userRelationService() {
        return userRelationService;
    }

    public static PostService postService() {
        return postService;
    }

    public static CommentService commentService() {
        return commentService;
    }
}
