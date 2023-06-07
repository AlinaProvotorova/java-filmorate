package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;

import static ru.yandex.practicum.filmorate.validators.Constants.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }


    @Override
    public User create(User user) {
        UserValidate.validateUser(user);
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        Integer id = user.getId();
        UserValidate.validateId(id);
        UserValidate.validateUser(user);
        return userStorage.update(user).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, id))
        );
    }

    @Override
    public void delete(Integer id) {
        UserValidate.validateId(id);
        if (!userStorage.delete(id)) {
            throw new NotFoundException(String.format(USER_NOT_FOUND, id));
        }
    }

    @Override
    public User getById(Integer id) {
        UserValidate.validateId(id);
        return userStorage.getById(id).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, id))
        );
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        if (!friendshipStorage.deleteFriend(userId, friendId)) {
            throw new NotFoundException(String.format("Дружбы между пользователями %s и %s не существует", userId, friendId));
        }
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
        UserValidate.validateId(userId);
        getById(userId);
        return friendshipStorage.getAllFriends(userId);
    }

    @Override
    public List<User> getIncomingFriendsRequests(Integer userId) {
        UserValidate.validateId(userId);
        getById(userId);
        return friendshipStorage.getIncomingFriendsRequests(userId);
    }

    @Override
    public List<User> getOutgoingFriendsRequests(Integer userId) {
        UserValidate.validateId(userId);
        getById(userId);
        return friendshipStorage.getOutgoingFriendsRequests(userId);
    }

    @Override
    public void sendFriendshipRequest(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        getById(userId);
        getById(friendId);
        friendshipStorage.sendFriendshipRequest(userId, friendId);
    }

    @Override
    public void acceptFriendship(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        getById(userId);
        getById(friendId);
        friendshipStorage.acceptFriendship(userId, friendId);
    }

    @Override
    public void rejectFriendship(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        getById(userId);
        getById(friendId);
        friendshipStorage.rejectFriendship(userId, friendId);
    }

    @Override
    public List<User> getFriendsOfFriend(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        getById(userId);
        getById(friendId);
        return friendshipStorage.getFriendsOfFriend(userId, friendId);
    }
}
