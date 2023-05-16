package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface LikesStorage {
    Optional<Film> like(Integer id, Integer userId);

    Optional<Film> dislike(Integer id, Integer userId);

    List<Film> getPopularFilms(Integer count);
}
