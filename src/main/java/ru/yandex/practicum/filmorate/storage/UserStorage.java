package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface UserStorage {

    Map<Integer, User> users = new HashMap<>();

    List<User> getAll();

    Map<Integer, User> getUsers();

    User create(User user);

    User update(User user);

    void delete(User user);

    User getById(Integer id);
}
