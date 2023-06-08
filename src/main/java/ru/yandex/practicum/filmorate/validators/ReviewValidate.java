package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;

import static ru.yandex.practicum.filmorate.validators.Constants.ID_NOT_POSITIVE;

public class ReviewValidate {

    public static void validateReview(Review review) {
        if (review.getIsPositive() == null ) {
            throw new ValidationException("Позитив не может быть пустым");
        }
        if (review.getUserId() == null) {
            throw new ValidationException("UserId не может быть пустым");
        }

        if (review.getFilmId() == null) {
            throw new ValidationException("FilmId не может быть пустым");
        }
    }

    public static void validateId(Integer id) {
        if (id <= 0) {
            throw new NotFoundException(ID_NOT_POSITIVE);
        }
    }
}
