package site.study.post.domain.content;

public class PostContent extends Content {

    private static final int MINIMUM_LENGTH = 5;
    private static final int MAXIMUM_LENGTH = 500;

    public PostContent(final String content) {
        super(content);
    }

    @Override
    protected void validateContentLength(final String content) {
         if (content.length() < MINIMUM_LENGTH || content.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException();
        }
    }
}
