package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface GenreStorage {

    List<Genre> getAll();

    Set<Genre> getGenresByFilm(Integer filmId);

    Genre create(Genre genre);

    Optional<Genre> update(Genre genre);

    boolean delete(Integer id);

    Optional<Genre> getById(Integer id);

}
