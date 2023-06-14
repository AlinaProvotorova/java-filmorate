package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.validators.RatingValidate;

import java.util.List;

import static ru.yandex.practicum.filmorate.validators.Constants.DIRECTOR_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorStorage directorStorage;

    @Override
    public List<Director> getAll() {
        return directorStorage.getAll();
    }

    @Override
    public Director create(Director director) {
        return directorStorage.create(director);
    }

    @Override
    public Director update(Director director) {
        return directorStorage.update(director).orElseThrow(() ->
                new NotFoundException(String.format(DIRECTOR_NOT_FOUND, director.getId())));
    }

    @Override
    public void delete(Integer id) {
        RatingValidate.validateId(id);
        if (!directorStorage.delete(id)) {
            throw new NotFoundException(String.format(DIRECTOR_NOT_FOUND, id));
        }
    }

    @Override
    public Director getById(Integer id) {
        return directorStorage.getById(id).orElseThrow(() ->
                new NotFoundException(String.format(DIRECTOR_NOT_FOUND, id)));
    }
}