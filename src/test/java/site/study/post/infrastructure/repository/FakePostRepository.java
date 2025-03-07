package site.study.post.infrastructure.repository;

import site.study.post.application.port.PostRepository;
import site.study.post.domain.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakePostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(final Post post) {
        if (post.getId() != null) {
            store.put(post.getId(), post);
            return post;
        }

        final long id = store.size() + 1;
        final Post newPost = post.withId(id);
        store.put(id, newPost);

        return newPost;
    }

    @Override
    public Optional<Post> findById(final Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
