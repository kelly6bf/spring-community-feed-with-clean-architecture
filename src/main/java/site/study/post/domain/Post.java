package site.study.post.domain;

import site.study.common.domain.PositiveIntegerCounter;
import site.study.post.domain.content.Content;
import site.study.post.domain.content.PostContent;
import site.study.user.domain.User;

import java.util.Objects;

public class Post {

    private final Long id;
    private final User author;
    private final PostContent content;
    private final PositiveIntegerCounter likeCounter;

    private PostPublicationState state;

    public static Post createPost(
        final User author,
        final String content,
        final PostPublicationState state
    ) {
        return new Post(null, author, new PostContent(content), new PositiveIntegerCounter(), state);
    }

    public Post(
        final Long id,
        final User author,
        final PostContent content,
        final PositiveIntegerCounter likeCounter,
        final PostPublicationState state
    ) {
        validateAuthor(author);

        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCounter = likeCounter;
        this.state = state;
    }

    private void validateAuthor(final User author) {
        if (author == null) {
            throw new IllegalArgumentException();
        }
    }

    public void like(final User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        likeCounter.increase();
    }

    public void unlike(final User user) {
        if (this.author.equals(user)) {
            throw new IllegalArgumentException();
        }
        likeCounter.decrease();
    }

    public void update(final User user, final String updateContent, final PostPublicationState state) {
        if (!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.content.updateContent(updateContent);
        this.state = state;
    }

    public Post withId(final Long id) {
        return new Post(id, this.author, this.content, this.likeCounter, this.state);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Content getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCounter.getCount();
    }

    public PostPublicationState getState() {
        return state;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
