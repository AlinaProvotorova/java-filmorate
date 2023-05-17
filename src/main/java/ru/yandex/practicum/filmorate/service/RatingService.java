package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;


public interface RatingService {

    List<Rating> getAll();

    Rating create(Rating rating);

    Rating update(Rating rating);

    void delete(Integer id);

    Rating getById(Integer id);

}
