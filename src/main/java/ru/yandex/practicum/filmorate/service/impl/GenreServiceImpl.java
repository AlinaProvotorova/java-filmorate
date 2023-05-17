package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validators.GenreValidate;

import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.validators.Constants.FILM_NOT_FOUND;
import static ru.yandex.practicum.filmorate.validators.Constants.GENRE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {


    private final GenreStorage genreStorage;
    private final FilmStorage filmStorage;


    @Override
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    @Override
    public Set<Genre> getGenresByFilm(Integer filmId) {
        filmStorage.getById(filmId).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, filmId))
        );
        return genreStorage.getGenresByFilm(filmId);
    }

    @Override
    public Genre create(Genre genre) {
        return genreStorage.create(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return genreStorage.update(genre).orElseThrow(
                () -> new NotFoundException(String.format(GENRE_NOT_FOUND, genre.getId()))
        );
    }

    @Override
    public void delete(Integer id) {
        GenreValidate.validateId(id);
        if (!genreStorage.delete(id)) {
            throw new NotFoundException(String.format(GENRE_NOT_FOUND, id));
        }
    }

    @Override
    public Genre getById(Integer id) {
        return genreStorage.getById(id).orElseThrow(
                () -> new NotFoundException(String.format(GENRE_NOT_FOUND, id))
        );
    }
}
