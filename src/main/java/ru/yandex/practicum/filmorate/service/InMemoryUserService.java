package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.*;


@Service
@RequiredArgsConstructor
public class InMemoryUserService implements UserStorage, UserService {

    private final UserStorage userStorage;

    @Override
    public User addFriend(Integer idUser, Integer idFriend) {
        UserValidate.validateNotFoundId(users.keySet(), idUser);
        UserValidate.validateNotFoundId(users.keySet(), idFriend);
        return users.get(idUser).addFriend(users.get(idFriend));
    }

    @Override
    public void deleteFriend(Integer idUser, Integer idFriend) {
        UserValidate.validateNotFoundId(users.keySet(), idUser);
        UserValidate.validateNotFoundId(users.keySet(), idFriend);
        users.get(idUser).deleteFriend(users.get(idFriend));
    }

    @Override
    public List<User> getFriends(Integer id) {
        UserValidate.validateNotFoundId(users.keySet(), id);
        List<User> friends = new ArrayList<>();
        for (Integer idFriend : users.get(id).getIdFriends()) {
            friends.add(users.get(idFriend));
        }
        return friends;
    }

    @Override
    public List<User> getFriendsOfFriend(Integer id, Integer otherId) {
        UserValidate.validateNotFoundId(users.keySet(), id);
        UserValidate.validateNotFoundId(users.keySet(), otherId);
        Set<Integer> intersection = users.get(id).getIdFriends();
        intersection.retainAll(users.get(otherId).getIdFriends());
        List<User> friends = new ArrayList<>();
        for (Integer idFriend : intersection) {
            friends.add(users.get(idFriend));
        }
        return friends;
    }


    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public Map<Integer, User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User create(User user) {
        UserValidate.validateUser(user);
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        UserValidate.validateNotFoundId(users.keySet(), user.getId());
        UserValidate.validateUser(user);
        return userStorage.update(user);
    }

    @Override
    public void delete(User user) {
        UserValidate.validateNotFoundId(users.keySet(), user.getId());
        userStorage.delete(user);
    }

    @Override
    public User getById(Integer id) {
        UserValidate.validateNotFoundId(users.keySet(), id);
        return userStorage.getById(id);
    }
}
