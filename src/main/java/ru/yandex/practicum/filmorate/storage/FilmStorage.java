package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();

    List<Film> getAll();

    Film create(Film film);

    Film update(Film film);

    void delete(Film film);

    Film getById(Integer id);
}
