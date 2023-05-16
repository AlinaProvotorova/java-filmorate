package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.util.List;


@Component
@Qualifier("friendshipDbStorage")
@Slf4j
public class FriendshipDbStorage extends UserDbStorage implements FriendshipStorage {

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<User> getAllFriends(Integer userId) {
        String sql = "SELECT u.ID , u.LOGIN , u.EMAIL , u.NAME, u.BIRTHDAY " +
                "FROM FRIENDSHIP f JOIN USERS u ON u.ID = f.FRIEND_ID " +
                "WHERE USER_ID=? AND IS_ACCEPTED=TRUE";
        return jdbcTemplate.query(sql, super::makeUser, userId);
    }

    @Override
    public List<User> getIncomingFriendsRequests(Integer userId) {
        String sql = "SELECT u.ID , u.LOGIN , u.EMAIL , u.NAME, u.BIRTHDAY " +
                "FROM FRIENDSHIP f JOIN USERS u ON u.ID = f.USER_ID " +
                "WHERE FRIEND_ID=? AND IS_ACCEPTED=FALSE";
        return jdbcTemplate.query(sql, super::makeUser, userId);
    }

    @Override
    public List<User> getOutgoingFriendsRequests(Integer userId) {
        String sql = "SELECT u.ID , u.LOGIN , u.EMAIL , u.NAME, u.BIRTHDAY " +
                "FROM FRIENDSHIP f JOIN USERS u ON u.ID = f.FRIEND_ID " +
                "WHERE USER_ID=? AND IS_ACCEPTED=FALSE;";
        return jdbcTemplate.query(sql, super::makeUser, userId);
    }

    @Override
    public boolean sendFriendshipRequest(Integer userId, Integer friendId) {
        String sql = "INSERT INTO friendship (user_id, friend_id, IS_ACCEPTED) VALUES (?, ?, TRUE)";
        // Изначально я здесь реализовала другую дружбу(с принятием заявок и отклонением,
        // код есть ниже, но по тз это не верно и тесты не проходят, хочу оставить данный код для дальнейшего
//        String sql = "INSERT INTO friendship (user_id, friend_id, ) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                userId,
                friendId
        );
        return true;
    }

    @Override
    public void acceptFriendship(Integer userId, Integer friendId) {
        String sqlAccept = "update friendship set " +
                "is_accepted = TRUE where user_id = ? and friend_id = ?";
        String sqlCreate = "INSERT INTO friendship (user_id, friend_id, is_accepted) VALUES (?, ?, TRUE)";
        jdbcTemplate.update(sqlAccept, friendId, userId);
        jdbcTemplate.update(sqlCreate, userId, friendId);
    }

    @Override
    public void rejectFriendship(Integer userId, Integer friendId) {
        String sql = "delete from friendship where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, friendId, userId);
    }

    @Override
    public List<User> getFriendsOfFriend(Integer userId, Integer friendId) {
        String sql = "SELECT u.ID , u.LOGIN , u.EMAIL , u.NAME, u.BIRTHDAY " +
                "FROM FRIENDSHIP f JOIN USERS u ON u.ID = f.FRIEND_ID " +
                "WHERE USER_ID = ? AND IS_ACCEPTED = TRUE AND FRIEND_ID IN " +
                "(SELECT  f2.FRIEND_ID FROM FRIENDSHIP f2 " +
                "WHERE USER_ID = ? AND IS_ACCEPTED = TRUE)";
        return jdbcTemplate.query(sql, super::makeUser, userId, friendId);
    }


    @Override
    public boolean deleteFriend(Integer userId, Integer friendId) {
        String sql = "delete from friendship where user_id = ? and friend_id = ? and is_accepted = TRUE";
        boolean delete1 = jdbcTemplate.update(sql, friendId, userId) > 0;
        boolean delete2 = jdbcTemplate.update(sql, userId, friendId) > 0;
        return delete1 || delete2;
    }
}
