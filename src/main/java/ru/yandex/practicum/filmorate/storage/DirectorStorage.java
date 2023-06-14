package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DirectorStorage {
    List<Director> getAll();

    Director create(Director director);

    Optional<Director> update(Director director);

    boolean delete(Integer id);

    Optional<Director> getById(Integer id);

    Set<Director> getDirectorsByFilm(Integer filmId);
}
