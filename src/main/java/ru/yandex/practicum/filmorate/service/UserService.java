package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addFriend(Integer idUser, Integer idFriend);

    void deleteFriend(Integer idUser, Integer idFriend);

    List<User> getFriends(Integer id);

    List<User> getFriendsOfFriend(Integer id, Integer otherId);
}
