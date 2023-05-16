package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class RatingController {

    private final RatingService service;

    @GetMapping("/{id}")
    public Optional<Rating> getGenreById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Rating> findAll() {
        return service.getAll();
    }

    @PostMapping
    public Rating create(@RequestBody Rating rating) {
        return service.create(rating);
    }

    @PutMapping
    public Optional<Rating> updateFilm(@RequestBody Rating rating) {
        return service.update(rating);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Райтинг " + id + " удален";
    }
}
