package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    List<Film> getAll();

    Film create(Film film);

    Film update(Film film);

    void delete(Integer id);

    Film getById(Integer id);

    Film like(Integer id, Integer userId);

    Film dislike(Integer id, Integer userId);

    List<Film> getAllFilmsOfDirector(Integer id, String sortBy);

    List<Film> getPopularFilms(Integer count, Integer genreId, Integer year);

    List<Film> getCommonFilms(Integer userId, Integer friendId);

    List<Film> searchFilms(String queryString, String searchBy);
}
