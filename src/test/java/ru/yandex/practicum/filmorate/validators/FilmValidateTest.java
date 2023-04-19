package ru.yandex.practicum.filmorate.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidateTest {
    Film film;

    @BeforeEach
    void setUp() {
        film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.now())
                .duration(60)
                .build();
    }

    @Test
    void validateName() {
        film.setName("");
        assertThrows(
                ValidationException.class,
                () -> FilmValidate.validateFilm(film),
                "Нельзя передать пустое название");
    }

    @Test
    void validateDescription() {
        film.setDescription("q".repeat(201));
        assertThrows(
                ValidationException.class,
                () -> FilmValidate.validateFilm(film),
                "Описание фильма должно быть ограниченно 200 символами");
    }

    @Test
    void validateReleaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(
                ValidationException.class,
                () -> FilmValidate.validateFilm(film),
                "Дата релиза — не может быть раньше 28 декабря 1895 года");
    }

    @Test
    void validateDuration() {
        film.setDuration(-1);
        assertThrows(
                ValidationException.class,
                () -> FilmValidate.validateFilm(film),
                "Продолжительность фильма должна быть положительной");
    }
}