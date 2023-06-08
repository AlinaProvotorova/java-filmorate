package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorService {
    List<Director> getAll();

    Director create(Director director);

    Director update(Director director);

    boolean delete(Integer id);

    Director getById(Integer id);
}
