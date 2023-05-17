package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.validators.Constants.ID_NOT_POSITIVE;

public class FilmValidate {

    public static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().equals("")) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    public static void validateId(Integer id) {
        if (id < 0) {
            throw new NotFoundException(ID_NOT_POSITIVE);
        }
    }

}
