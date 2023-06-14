package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface LikesStorage {
    Optional<Film> like(Integer id, Integer userId);

    Optional<Film> dislike(Integer id, Integer userId);

    List<Film> getPopularFilms(Integer count, Integer genreId, Integer year);

    List<User> getLikesByFilm(Integer filmId);

    List<Film> getFavoriteFilmsByUser(Integer userId);

    List<Film> getCommonFilms(Integer userId, Integer friendId);
}
