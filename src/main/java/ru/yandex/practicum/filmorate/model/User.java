package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class User {
    private int id;
    @NotBlank
    @NotNull
    private String login;
    @Email
    @NotBlank
    @NotNull
    private String email;
    private String name;
    private LocalDate birthday;

}
