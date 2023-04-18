package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
public class Film {
    private int id;
    @NotBlank
    @NotNull
    private String name;
    private LocalDate releaseDate;
    private String description;
    private Integer duration;
}
