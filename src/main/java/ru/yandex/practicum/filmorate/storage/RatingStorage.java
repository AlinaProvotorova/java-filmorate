package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;


public interface RatingStorage {

    List<Rating> getAll();

    Rating create(Rating rating);

    Optional<Rating> update(Rating rating);

    boolean delete(Integer id);

    Optional<Rating> getById(Integer id);

}
