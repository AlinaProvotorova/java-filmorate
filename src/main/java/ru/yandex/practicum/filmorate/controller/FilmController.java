package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count,
            @RequestParam(value = "genreId", required = false) Integer genreId,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return service.getPopularFilms(count, genreId, year);
    }

    @GetMapping
    public List<Film> findAll() {
        return service.getAll();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return service.create(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return service.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film like(@PathVariable Integer id, @PathVariable Integer userId) {
        return service.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film dislike(@PathVariable Integer id, @PathVariable Integer userId) {
        return service.dislike(id, userId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Фильм " + id + " удален";
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam(value = "userId") Integer userId,
                                     @RequestParam(value = "friendId") Integer friendId) {
        return service.getCommonFilms(userId, friendId);
    }
}
