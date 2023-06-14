package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UserService {

    List<User> getAll();

    User create(User user);

    User update(User user);

    void delete(Integer id);

    User getById(Integer id);

    List<User> getAllFriends(Integer userId);

    List<User> getIncomingFriendsRequests(Integer userId);

    List<User> getOutgoingFriendsRequests(Integer userId);

    void sendFriendshipRequest(Integer userId, Integer friendId);

    void acceptFriendship(Integer userId, Integer friendId);

    void rejectFriendship(Integer userId, Integer friendId);

    List<User> getFriendsOfFriend(Integer id, Integer otherId);

    void deleteFriend(Integer idUser, Integer idFriend);

    List<Film> recommendations(Integer id);

}
