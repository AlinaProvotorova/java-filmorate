package ru.yandex.practicum.filmorate.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
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
        assertThrows(ValidationException.class,()-> FilmValidate.validateFilm(film), FilmValidate.validateName);
    }

    @Test
    void validateDescription() {
        film.setDescription("q".repeat(201));
        assertThrows(ValidationException.class,()-> FilmValidate.validateFilm(film), FilmValidate.validateDescription);
    }

    @Test
    void validateReleaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class,()-> FilmValidate.validateFilm(film), FilmValidate.validateReleaseDate);
    }

    @Test
    void validateDuration() {
        film.setDuration(-1);
        assertThrows(ValidationException.class,()-> FilmValidate.validateFilm(film), FilmValidate.validateDuration);
    }
}