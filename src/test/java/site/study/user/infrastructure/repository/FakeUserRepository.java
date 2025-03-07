package site.study.user.infrastructure.repository;

import site.study.user.application.port.UserRepository;
import site.study.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();

    @Override
    public User save(final User user) {
        if (user.getId() != null) {
            store.put(user.getId(), user);
        }

        final long newUserId = store.size() + 1L;
        final User newUser = new User(newUserId, user.getInfo());
        store.put(newUserId, newUser);
        return newUser;
    }

    @Override
    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
