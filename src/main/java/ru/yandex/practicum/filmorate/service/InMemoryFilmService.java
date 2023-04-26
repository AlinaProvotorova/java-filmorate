package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmStorage, FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film like(Integer id, Integer userId) {
        FilmValidate.validateNotFoundId(films.keySet(), id);
        UserValidate.validateNotFoundId(userStorage.getUsers().keySet(), userId);
        Film film = films.get(id);
        film.addLikes(userId);
        return film;
    }

    @Override
    public Film dislike(Integer id, Integer userId) {
        FilmValidate.validateNotFoundId(films.keySet(), id);
        UserValidate.validateNotFoundId(userStorage.getUsers().keySet(), userId);
        Film film = films.get(id);
        film.delLikes(userId);
        return film;
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        ArrayList<Film> filmsV = new ArrayList<>(films.values());
        return filmsV.stream()
                .sorted((f0, f1) -> -1 * f0.getLenLikes().compareTo(f1.getLenLikes()))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film create(Film film) {
        FilmValidate.validateFilm(film);
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        FilmValidate.validateNotFoundId(films.keySet(), film.getId());
        FilmValidate.validateFilm(film);
        return filmStorage.update(film);
    }

    @Override
    public void delete(Film film) {
        FilmValidate.validateNotFoundId(films.keySet(), film.getId());
        filmStorage.delete(film);
    }

    @Override
    public Film getById(Integer id) {
        Film film = filmStorage.getById(id);
        FilmValidate.validateNotFoundId(films.keySet(), id);
        return film;
    }
}
