package site.study.post.infrastructure.repository;

import site.study.post.application.port.LikeRepository;
import site.study.post.domain.Post;
import site.study.post.domain.comment.Comment;
import site.study.user.domain.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FakeLikeRepository implements LikeRepository {

    private final Map<Post, Set<User>> postLikes = new HashMap<>();
    private final Map<Comment, Set<User>> commentLikes = new HashMap<>();

    @Override
    public boolean checkLike(final Post post, final User user) {
        if (postLikes.get(post) == null) {
            return false;
        }

        return postLikes.get(post).contains(user);
    }

    @Override
    public void like(final Post post, final User user) {
        Set<User> users = postLikes.get(post);
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
        postLikes.put(post, users);
    }

    @Override
    public void unlike(final Post post, final User user) {
        Set<User> users = postLikes.get(post);
        if (users == null) {
            return;
        }
        users.remove(user);
        postLikes.put(post, users);
    }

    @Override
    public boolean checkLike(final Comment comment, final User user) {
        if (commentLikes.get(comment) == null) {
            return false;
        }

        return commentLikes.get(comment).contains(user);
    }

    @Override
    public void like(final Comment comment, final User user) {
        Set<User> users = commentLikes.get(comment);
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
        commentLikes.put(comment, users);
    }

    @Override
    public void unlike(final Comment comment, final User user) {
        Set<User> users = commentLikes.get(comment);
        if (users == null) {
            return;
        }
        users.remove(user);
        commentLikes.put(comment, users);
    }
}
