package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
