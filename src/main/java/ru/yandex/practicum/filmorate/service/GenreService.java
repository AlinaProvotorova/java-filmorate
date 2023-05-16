package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.GenreValidate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService implements GenreStorage {


    private final GenreStorage genreStorage;
    private final FilmStorage filmStorage;


    @Override
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    @Override
    public Set<Genre> getGenresByFilm(Integer filmId) {
        FilmValidate.validateOptoinalFilm(filmStorage.getById(filmId), filmId);
        return genreStorage.getGenresByFilm(filmId);
    }

    @Override
    public Genre create(Genre genre) {
        return genreStorage.create(genre);
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        return GenreValidate.validateOptoinalGenre(
                genreStorage.update(genre), genre.getId()
        );
    }

    @Override
    public boolean delete(Integer id) {
        return GenreValidate.validateDeleteGenre(
                genreStorage.delete(id), id
        );
    }

    @Override
    public Optional<Genre> getById(Integer id) {
        return GenreValidate.validateOptoinalGenre(
                genreStorage.getById(id), id
        );
    }
}
