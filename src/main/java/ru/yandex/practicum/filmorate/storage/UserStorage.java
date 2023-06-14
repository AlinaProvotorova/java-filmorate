package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;


public interface UserStorage {

    List<User> getAll();

    User create(User user);

    Optional<User> update(User user);

    boolean delete(Integer id);

    Optional<User> getById(Integer id);

    List<Film> recommendations(Integer id);

}
