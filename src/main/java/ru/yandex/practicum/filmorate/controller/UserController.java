package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController{
    private final Map<Integer, User> users = new HashMap<>();
    static private int nextId = 1;

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        UserValidate.validateUser(user);
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!(users.containsKey(user.getId()))){
            throw new RuntimeException("Такого пользователя еще нет");
        }
        UserValidate.validateUser(user);
        users.put(user.getId(), user);
        return user;
    }

}
