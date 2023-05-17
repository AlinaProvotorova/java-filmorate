package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;

import static ru.yandex.practicum.filmorate.validators.Constants.ID_NOT_POSITIVE;

public class RatingValidate {

    public static void validateId(Integer id) {
        if (id < 0) {
            throw new NotFoundException(ID_NOT_POSITIVE);
        }
    }
}
