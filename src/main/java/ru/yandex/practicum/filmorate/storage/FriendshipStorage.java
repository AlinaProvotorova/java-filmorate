package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface FriendshipStorage {

    List<User> getAllFriends(Integer userId);

    List<User> getIncomingFriendsRequests(Integer userId);

    List<User> getOutgoingFriendsRequests(Integer userId);

    void sendFriendshipRequest(Integer userId, Integer friendId);

    void acceptFriendship(Integer userId, Integer friendId);

    void rejectFriendship(Integer userId, Integer friendId);

    List<User> getFriendsOfFriend(Integer id, Integer otherId);

    boolean deleteFriend(Integer idUser, Integer idFriend);
}
