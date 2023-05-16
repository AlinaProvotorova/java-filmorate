package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.validators.RatingValidate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService implements RatingStorage {


    private final RatingStorage ratingStorage;

    @Override
    public List<Rating> getAll() {
        return ratingStorage.getAll();
    }


    @Override
    public Rating create(Rating rating) {
        return ratingStorage.create(rating);
    }

    @Override
    public Optional<Rating> update(Rating rating) {
        return RatingValidate.validateOptoinalRating(
                ratingStorage.update(rating), rating.getId()
        );
    }

    @Override
    public boolean delete(Integer id) {
        return RatingValidate.validateDeleteRating(
                ratingStorage.delete(id), id
        );
    }

    @Override
    public Optional<Rating> getById(Integer id) {
        return RatingValidate.validateOptoinalRating(
                ratingStorage.getById(id), id
        );
    }
}
