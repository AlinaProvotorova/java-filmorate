package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping("/{id}")
    public Director getDirectorById(@PathVariable Integer id) {
        return directorService.getById(id);
    }

    @GetMapping
    public List<Director> findAll() {
        return directorService.getAll();
    }

    @PostMapping
    public Director create(@RequestBody Director director) {
        return directorService.create(director);
    }

    @PutMapping
    public Director updateDirector(@RequestBody Director director) {
        return directorService.update(director);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        directorService.delete(id);
        return "Режиссёр c " + id + " удален.";
    }
}
