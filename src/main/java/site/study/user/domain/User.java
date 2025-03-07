package site.study.user.domain;

import site.study.common.domain.PositiveIntegerCounter;

import java.util.Objects;

public class User {

    private final Long id;
    private final UserInfo info;
    private final PositiveIntegerCounter followingCounter;
    private final PositiveIntegerCounter followerCounter;

    public User(final Long id, final UserInfo info) {
        this(id, info, new PositiveIntegerCounter(), new PositiveIntegerCounter());
    }

    public User(
        final Long id,
        final UserInfo info,
        final PositiveIntegerCounter followingCounter,
        final PositiveIntegerCounter followerCounter
    ) {
        this.id = id;
        this.info = info;
        this.followingCounter = followingCounter;
        this.followerCounter = followerCounter;
    }

    public void follow(final User targetUser) {
        if (targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.increase();
        targetUser.increaseFollowerCount();
    }

    private void increaseFollowerCount() {
        followerCounter.increase();
    }

    public void unfollow(final User targetUser) {
        if (targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.decrease();
        targetUser.decreaseFollowerCount();
    }

    private void decreaseFollowerCount() {
        followerCounter.decrease();
    }

    public Long getId() {
        return id;
    }

    public UserInfo getInfo() {
        return info;
    }

    public int getFollowingCount() {
        return followingCounter.getCount();
    }

    public int getFollowerCount() {
        return followerCounter.getCount();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
