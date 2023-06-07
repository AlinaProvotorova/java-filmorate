package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    List<Film> getAll();

    Film create(Film film);

    Optional<Film> update(Film film);

    boolean delete(Integer id);

    Optional<Film> getById(Integer id);
}
