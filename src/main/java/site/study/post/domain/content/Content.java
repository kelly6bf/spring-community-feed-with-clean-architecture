package site.study.post.domain.content;

import site.study.common.domain.DatetimeInfo;

public abstract class Content {

    final DatetimeInfo datetimeInfo;

    String content;

    public Content(final String content) {
        checkNullOrBlank(content);
        validateContentLength(content);

        this.datetimeInfo = new DatetimeInfo();
        this.content = content;
    }

    private void checkNullOrBlank(final String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public void updateContent(final String updateContent) {
        checkNullOrBlank(updateContent);
        validateContentLength(updateContent);

        this.datetimeInfo.updateEditDatetime();
        this.content = updateContent;
    }

    public boolean isEdited() {
        return datetimeInfo.isEdited();
    }

    public String getContentText() {
        return content;
    }

    protected abstract void validateContentLength(String content);
}
