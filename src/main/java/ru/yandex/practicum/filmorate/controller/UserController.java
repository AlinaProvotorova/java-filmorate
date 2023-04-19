package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ErrorMessage;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidate;


import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    static int nextId = 1;

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public Object create(@RequestBody User user) {
        UserValidate.validateUser(user);
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!(users.containsKey(user.getId()))) {
            throw new ValidationException(String.format("Пользователя с id %s не существует", user.getId()));
        }
        UserValidate.validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ErrorMessage handlerExeption(ValidationException e) {
        log.debug(e.getMessage());
        return new ErrorMessage(e.getMessage());
    }
}
