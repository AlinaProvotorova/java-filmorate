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

    List<Film> getPopularFilms(Integer count);

    List<Film> getAllFilmsOfDirector(Integer id, String sortBy);
}
