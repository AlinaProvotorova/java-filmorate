package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    Film like(Integer id, Integer userId);

    Film dislike(Integer id, Integer userId);

    List<Film> getPopularFilms(Integer count);
}
