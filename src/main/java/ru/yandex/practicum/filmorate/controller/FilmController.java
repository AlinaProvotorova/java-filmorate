package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @GetMapping("/{id}")
    public Optional<Film> getFilmById(@PathVariable Integer id) {
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

    @PostMapping
    public Film create(@RequestBody Film film) {
        return service.create(film);
    }

    @PutMapping
    public Optional<Film> updateFilm(@RequestBody Film film) {
        return service.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Optional<Film> like(@PathVariable Integer id, @PathVariable Integer userId) {
        return service.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Optional<Film> dislike(@PathVariable Integer id, @PathVariable Integer userId) {
        return service.dislike(id, userId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Фильм " + id + " удален";
    }
}
