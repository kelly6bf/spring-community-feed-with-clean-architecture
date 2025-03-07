package site.study.user.application;

import site.study.user.application.dto.FollowUserRequestDto;
import site.study.user.application.port.UserRelationRepository;
import site.study.user.domain.User;

public class UserRelationService {

    private final UserRelationRepository userRelationRepository;
    private final UserService userService;

    public UserRelationService(final UserRelationRepository userRelationRepository, final UserService userService) {
        this.userRelationRepository = userRelationRepository;
        this.userService = userService;
    }

    public void follow(final FollowUserRequestDto requestDto) {
        final User user = userService.getUser(requestDto.userId());
        final User targetUser = userService.getUser(requestDto.targetUserId());

        if (userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException();
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    public void unfollow(final FollowUserRequestDto requestDto) {
        final User user = userService.getUser(requestDto.userId());
        final User targetUser = userService.getUser(requestDto.targetUserId());

        if (!userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException();
        }

        user.unfollow(targetUser);
        userRelationRepository.delete(user, targetUser);
    }
}
