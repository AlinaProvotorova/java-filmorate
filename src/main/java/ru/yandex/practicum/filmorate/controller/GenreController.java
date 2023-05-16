package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;

    @GetMapping("/{id}")
    public Optional<Genre> getGenreById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Genre> findAll() {
        return service.getAll();
    }

    @GetMapping("/film/{id}")
    public Set<Genre> getGenresByGenre(@PathVariable Integer id) {
        return service.getGenresByFilm(id);
    }

    @PostMapping
    public Genre create(@RequestBody Genre genre) {
        return service.create(genre);
    }

    @PutMapping
    public Optional<Genre> updateGenre(@RequestBody Genre genre) {
        return service.update(genre);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Жанр " + id + " удален";
    }
}
