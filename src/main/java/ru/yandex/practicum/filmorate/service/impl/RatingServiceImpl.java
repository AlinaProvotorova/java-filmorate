package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.validators.RatingValidate;

import java.util.List;

import static ru.yandex.practicum.filmorate.validators.Constants.RATING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


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
    public Rating update(Rating rating) {
        return ratingStorage.update(rating).orElseThrow(
                () -> new NotFoundException(String.format(RATING_NOT_FOUND, rating.getId()))
        );
    }

    @Override
    public void delete(Integer id) {
        RatingValidate.validateId(id);
        if (!ratingStorage.delete(id)) {
            throw new NotFoundException(String.format(RATING_NOT_FOUND, id));
        }
    }

    @Override
    public Rating getById(Integer id) {
        return ratingStorage.getById(id).orElseThrow(
                () -> new NotFoundException(String.format(RATING_NOT_FOUND, id))
        );
    }
}
