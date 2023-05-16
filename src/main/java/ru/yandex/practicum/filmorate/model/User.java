package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String email;
    private String name;
    private LocalDate birthday;


    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("login", login);
        values.put("email", email);
        values.put("birthday", birthday);
        values.put("name", name);
        return values;
    }
}
