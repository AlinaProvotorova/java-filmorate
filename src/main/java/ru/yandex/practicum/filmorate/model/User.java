package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String email;
    private String name;
    private LocalDate birthday;
}
