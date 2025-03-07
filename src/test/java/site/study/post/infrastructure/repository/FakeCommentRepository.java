package site.study.post.infrastructure.repository;

import site.study.post.application.port.CommentRepository;
import site.study.post.domain.comment.Comment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeCommentRepository implements CommentRepository {

    private final Map<Long, Comment> store = new HashMap<>();

    @Override
    public Comment save(final Comment comment) {
        if (comment.getId() != null) {
            store.put(comment.getId(), comment);
            return comment;
        }

        final long id = store.size() + 1;
        final Comment newComment = comment.withId(id);
        store.put(id, newComment);

        return newComment;
    }

    @Override
    public Optional<Comment> findById(final Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
