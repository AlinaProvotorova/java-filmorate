package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
public class Film {
    private int id;
    private String name;
    private LocalDate releaseDate;
    private String description;
    private Integer duration;
}
