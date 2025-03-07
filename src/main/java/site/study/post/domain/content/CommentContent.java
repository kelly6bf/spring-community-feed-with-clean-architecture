package site.study.post.domain.content;

public class CommentContent extends Content{

    private static final int MAXIMUM_LENGTH = 100;

    public CommentContent(final String content) {
        super(content);
    }

    @Override
    protected void validateContentLength(final String content) {
        if (content.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException();
        }
    }
}
