package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserStorage, FriendshipStorage {

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
    public Optional<User> update(User user) {
        UserValidate.validateId(user.getId());
        UserValidate.validateUser(user);
        return UserValidate.validateOptoinalUser(
                userStorage.update(user), user.getId()
        );
    }

    @Override
    public boolean delete(Integer id) {
        return UserValidate.validateDeleteUser(
                userStorage.delete(id), id
        );
    }

    @Override
    public Optional<User> getById(Integer id) {
        return UserValidate.validateOptoinalUser(
                userStorage.getById(id), id
        );
    }

    @Override
    public boolean deleteFriend(Integer userId, Integer friendId) {
        return UserValidate.validateFriendship(
                friendshipStorage.deleteFriend(userId, friendId),
                userId, friendId
        );
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        return friendshipStorage.getAllFriends(userId);
    }

    @Override
    public List<User> getIncomingFriendsRequests(Integer userId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        return friendshipStorage.getIncomingFriendsRequests(userId);
    }

    @Override
    public List<User> getOutgoingFriendsRequests(Integer userId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        return friendshipStorage.getOutgoingFriendsRequests(userId);
    }

    @Override
    public boolean sendFriendshipRequest(Integer userId, Integer friendId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        UserValidate.validateOptoinalUser(getById(friendId), friendId);
        return friendshipStorage.sendFriendshipRequest(userId, friendId);
    }

    @Override
    public void acceptFriendship(Integer userId, Integer friendId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        UserValidate.validateOptoinalUser(getById(friendId), friendId);
        friendshipStorage.acceptFriendship(userId, friendId);
    }

    @Override
    public void rejectFriendship(Integer userId, Integer friendId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        UserValidate.validateOptoinalUser(getById(friendId), friendId);
        friendshipStorage.rejectFriendship(userId, friendId);
    }

    @Override
    public List<User> getFriendsOfFriend(Integer userId, Integer friendId) {
        UserValidate.validateOptoinalUser(getById(userId), userId);
        UserValidate.validateOptoinalUser(getById(friendId), friendId);
        return friendshipStorage.getFriendsOfFriend(userId, friendId);
    }
}
