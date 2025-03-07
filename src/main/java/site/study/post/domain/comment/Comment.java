package site.study.post.domain.comment;

import site.study.common.domain.PositiveIntegerCounter;
import site.study.post.domain.Post;
import site.study.post.domain.content.CommentContent;
import site.study.user.domain.User;

import java.util.Objects;

public class Comment {

    private final Long id;
    private final Post post;
    private final User author;
    private final CommentContent content;
    private final PositiveIntegerCounter likeCounter;

    public static Comment createComment(
        final Post post,
        final User author,
        final String content
    ) {
        return new Comment(null, post, author, new CommentContent(content));
    }

    public Comment(
        final Long id,
        final Post post,
        final User author,
        final CommentContent content
    ) {
        validateNull(post, author, content);

        this.id = id;
        this.post = post;
        this.author = author;
        this.content = content;
        this.likeCounter = new PositiveIntegerCounter();
    }

    private void validateNull(
        final Post post,
        final User author,
        final CommentContent content
    ) {
        if (post == null) {
            throw new IllegalArgumentException();
        }

        if (author == null) {
            throw new IllegalArgumentException();
        }

        if (content == null) {
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

    public void updateComment(final User user, String updateContent) {
        if (!this.author.equals(user)) {
            throw new IllegalArgumentException();
        }

        this.content.updateContent(updateContent);
    }

    public Comment withId(final Long id) {
        return new Comment(id, post, author, content);
    }

    public Long getId() {
        return id;
    }

    public CommentContent getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCounter.getCount();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
