package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Optional;

public class GenreValidate {

    public static boolean validateId(Integer id) {
        if (id < 0) {
            throw new NotFoundException("Id может быть только положительным");
        }
        return true;
    }

    public static Optional<Genre> validateOptoinalGenre(Optional<Genre> optionalGenre, Integer id) {
        validateId(id);
        if (optionalGenre.isEmpty()) {
            throw new NotFoundException(String.format("Жанра с id %s не существует", id));
        }
        return optionalGenre;
    }

    public static boolean validateDeleteGenre(boolean deleteGenre, Integer id) {
        validateId(id);
        if (!deleteGenre) {
            throw new NotFoundException(String.format("Жанра с id %s не существует", id));
        }
        return deleteGenre;
    }
}
