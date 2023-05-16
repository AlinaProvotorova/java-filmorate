package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.*;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.GenreValidate;
import ru.yandex.practicum.filmorate.validators.RatingValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService implements FilmStorage, LikesStorage {


    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;
    private final UserStorage userStorage;

    @Override
    public Optional<Film> like(Integer id, Integer userId) {
        FilmValidate.validateOptoinalFilm(filmStorage.getById(id), id);
        UserValidate.validateOptoinalUser(userStorage.getById(userId), id);
        return likesStorage.like(id, userId);
    }

    @Override
    public Optional<Film> dislike(Integer id, Integer userId) {
        FilmValidate.validateOptoinalFilm(filmStorage.getById(id), id);
        UserValidate.validateOptoinalUser(userStorage.getById(userId), id);
        return likesStorage.dislike(id, userId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        return likesStorage.getPopularFilms(count);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film create(Film film) {
        FilmValidate.validateFilm(film);

        Integer ratingId = film.getMpa().get().getId();
        RatingValidate.validateOptoinalRating(
                ratingStorage.getById(ratingId), ratingId
        );
        checkGenresFilm(film);
        return filmStorage.create(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        FilmValidate.validateId(film.getId());
        FilmValidate.validateFilm(film);

        Integer ratingId = film.getMpa().get().getId();
        RatingValidate.validateOptoinalRating(
                ratingStorage.getById(ratingId), ratingId
        );
        checkGenresFilm(film);
        return FilmValidate.validateOptoinalFilm(
                filmStorage.update(film), film.getId()
        );
    }

    @Override
    public boolean delete(Integer id) {
        return FilmValidate.validateDeleteFilm(
                filmStorage.delete(id), id
        );
    }

    @Override
    public Optional<Film> getById(Integer id) {
        return FilmValidate.validateOptoinalFilm(
                filmStorage.getById(id), id
        );
    }

    private void checkGenresFilm(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            film.getGenres().forEach(
                    genre -> GenreValidate.validateOptoinalGenre(
                            genreStorage.getById(genre.getId()), genre.getId()
                    )
            );
        }
    }
}
