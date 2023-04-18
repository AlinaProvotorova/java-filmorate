package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidate {
    public static String validateName = "Название не может быть пустым";
    public static String validateDescription = "Максимальная длина описания — 200 символов";
    public static String validateReleaseDate = "Дата релиза — не раньше 28 декабря 1895 года";
    public static String validateDuration = "Продолжительность фильма должна быть положительной";

    public static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().equals("")) {
            log.debug(validateName);
            throw new ValidationException(validateName);
        }
        if (film.getDescription().length() > 200) {
            log.debug(validateDescription);
            throw new ValidationException(validateDescription);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug(validateReleaseDate);
            throw new ValidationException(validateReleaseDate);
        }
        if (film.getDuration() < 0) {
            log.debug(validateDuration);
            throw new ValidationException(validateDuration);
        }
    }
}
