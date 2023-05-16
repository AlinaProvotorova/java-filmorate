package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface FriendshipStorage {

    List<User> getAllFriends(Integer user_id);

    List<User> getIncomingFriendsRequests(Integer user_id);

    List<User> getOutgoingFriendsRequests(Integer user_id);

    boolean sendFriendshipRequest(Integer user_id, Integer friend_id);

    void acceptFriendship(Integer user_id, Integer friend_id);

    void rejectFriendship(Integer user_id, Integer friend_id);

    List<User> getFriendsOfFriend(Integer id, Integer otherId);

    boolean deleteFriend(Integer idUser, Integer idFriend);
}
