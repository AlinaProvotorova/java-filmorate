package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.*;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.GenreValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.validators.Constants.*;


@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {


    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;
    private final UserStorage userStorage;
    private final DirectorStorage directorStorage;
    private final FeedStorage feedStorage;

    @Override
    public Film like(Integer id, Integer userId) {
        FilmValidate.validateId(id);
        UserValidate.validateId(id);
        userStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId))
        );
        getById(id);
        feedStorage.create(userId, id, EventType.LIKE, EventOperation.ADD);
        return likesStorage.like(id, userId).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, id))
        );
    }

    @Override
    public Film dislike(Integer id, Integer userId) {
        FilmValidate.validateId(id);
        UserValidate.validateId(id);
        userStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId))
        );
        getById(id);
        feedStorage.create(userId, id, EventType.LIKE, EventOperation.REMOVE);
        return likesStorage.dislike(id, userId).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, id))
        );
    }

    @Override
    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        if (genreId != null) {
            GenreValidate.validateId(genreId);
            genreStorage.getById(genreId).orElseThrow(
                    () -> new NotFoundException(String.format(GENRE_NOT_FOUND, genreId))
            );
        }
        if (year != null && year < 1895) {
            throw new ValidationException("Дата релиза — не раньше 1895 года");
        }
        return likesStorage.getPopularFilms(count, genreId, year);
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film create(Film film) {
        FilmValidate.validateFilm(film);
        checkRatingFilm(film);
        checkGenresFilm(film);
        checkDirectorsFilm(film.getDirectors());
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        FilmValidate.validateId(film.getId());
        FilmValidate.validateFilm(film);
        checkRatingFilm(film);
        checkGenresFilm(film);
        checkDirectorsFilm(film.getDirectors());
        return filmStorage.update(film).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, film.getId()))
        );
    }

    @Override
    public void delete(Integer id) {
        FilmValidate.validateId(id);
        if (!filmStorage.delete(id)) {
            throw new NotFoundException(String.format(FILM_NOT_FOUND, id));
        }
    }

    @Override
    public Film getById(Integer id) {
        return filmStorage.getById(id).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, id))
        );
    }

    @Override
    public List<Film> getAllFilmsOfDirector(Integer id, String sortBy) {
        directorStorage.getById(id).orElseThrow(() ->
                new NotFoundException(String.format(DIRECTOR_NOT_FOUND, id)));
        return filmStorage.getAllFilmsOfDirector(id, sortBy);
    }

    @Override
    public List<Film> getCommonFilms(Integer userId, Integer friendId) {
        UserValidate.validateId(userId);
        UserValidate.validateId(friendId);
        userStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));
        userStorage.getById(friendId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, friendId)));
        return likesStorage.getCommonFilms(userId, friendId);
    }

    private void checkRatingFilm(Film film) {
        Integer id = film.getMpa().getId();
        ratingStorage.getById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format(RATING_NOT_FOUND, id)
                )
        );
    }

    private void checkGenresFilm(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            film.getGenres().forEach(
                    genre -> genreStorage.getById(genre.getId())
                            .orElseThrow(() -> new NotFoundException(
                                    String.format(GENRE_NOT_FOUND, genre.getId())
                            ))
            );
        }
    }

    private void checkDirectorsFilm(Set<Director> directors) {
        if (directors != null && !directors.isEmpty()) {
            directors.stream().forEach(director ->
                    directorStorage.getById(director.getId()).orElseThrow(() ->
                            new NotFoundException(String.format(DIRECTOR_NOT_FOUND, director.getId())))
            );
        }
    }

    @Override
    public List<Film> searchFilms(String queryString, String searchBy) {
        return filmStorage.search(queryString, searchBy);
    }
}
