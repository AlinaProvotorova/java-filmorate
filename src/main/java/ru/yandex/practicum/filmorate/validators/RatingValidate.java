package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Optional;

public class RatingValidate {

    public static void validateId(Integer id) {
        if (id < 0) {
            throw new NotFoundException("Id может быть только положительным");
        }
    }

    public static Optional<Rating> validateOptoinalRating(Optional<Rating> optionalRating, Integer id) {
        validateId(id);
        if (optionalRating.isEmpty()) {
            throw new NotFoundException(String.format("Рейтинга с id %s не существует", id));
        }
        return optionalRating;
    }

    public static boolean validateDeleteRating(boolean deleteRating, Integer id) {
        validateId(id);
        if (!deleteRating) {
            throw new NotFoundException(String.format("Рейтинга с id %s не существует", id));
        }
        return deleteRating;
    }
}
