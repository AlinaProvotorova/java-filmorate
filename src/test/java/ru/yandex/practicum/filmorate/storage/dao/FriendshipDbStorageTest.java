package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FriendshipDbStorageTest {

    private final FriendshipDbStorage friendshipDbStorage;

    @Test
    void getAllFriends() {
        List<User> friends = friendshipDbStorage.getAllFriends(1);
        assertThat(friends).hasSize(2);
    }

    @Test
    void getIncomingFriendsRequests() {
        List<User> friends = friendshipDbStorage.getIncomingFriendsRequests(4);
        assertThat(friends).hasSize(2);
    }

    @Test
    void getOutgoingFriendsRequests() {
        List<User> friends = friendshipDbStorage.getOutgoingFriendsRequests(1);
        assertThat(friends).hasSize(2);
    }

    @Test
    void sendFriendshipRequest() {
        friendshipDbStorage.sendFriendshipRequest(2, 3);
        List<User> friends = friendshipDbStorage.getAllFriends(2);
        assertThat(friends).contains(friendshipDbStorage.getById(3).get());
    }

    @Test
    void acceptFriendship() {
        friendshipDbStorage.acceptFriendship(3, 1);
        List<User> friends = friendshipDbStorage.getAllFriends(3);
        List<User> friends2 = friendshipDbStorage.getAllFriends(1);
        assertThat(friends).contains(friendshipDbStorage.getById(1).get());
        assertThat(friends2).contains(friendshipDbStorage.getById(3).get());
    }

    @Test
    void rejectFriendship() {
        friendshipDbStorage.rejectFriendship(4, 1);
        List<User> friends = friendshipDbStorage.getAllFriends(4);
        List<User> friendsIncoming = friendshipDbStorage.getIncomingFriendsRequests(4);
        assertThat(friends).doesNotContain(friendshipDbStorage.getById(1).get());
        assertThat(friendsIncoming).doesNotContain(friendshipDbStorage.getById(1).get());
    }

    @Test
    void getFriendsOfFriend() {
        List<User> friendsOfFriend = friendshipDbStorage.getFriendsOfFriend(1, 5);
        assertThat(friendsOfFriend).hasSize(1);
        assertThat(friendsOfFriend).contains(friendshipDbStorage.getById(2).get());
    }

    @Test
    void deleteFriend() {
        friendshipDbStorage.deleteFriend(1, 5);
        List<User> friends = friendshipDbStorage.getAllFriends(1);
        assertThat(friends).hasSize(1);
        assertThat(friends).doesNotContain(friendshipDbStorage.getById(5).get());
    }
}