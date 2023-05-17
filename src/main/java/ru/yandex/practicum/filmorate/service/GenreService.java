package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;


public interface GenreService {

    List<Genre> getAll();

    Set<Genre> getGenresByFilm(Integer filmId);

    Genre create(Genre genre);

    Genre update(Genre genre);

    void delete(Integer id);

    Genre getById(Integer id);

}
