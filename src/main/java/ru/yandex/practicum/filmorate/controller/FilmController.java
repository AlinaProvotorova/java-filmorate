package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
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
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count
    ) {
        return service.getPopularFilms(count);
    }

    @GetMapping
    public List<Film> findAll() {
        return service.getAll();
    }

    @GetMapping("director/{directorId}")
    public List<Film> getSortedFilms(@PathVariable("directorId") Integer id, @RequestParam List<String> sortBy) {
        return null;
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
}
