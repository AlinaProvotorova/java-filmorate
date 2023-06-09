package ru.yandex.practicum.filmorate.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Builder
@Data
public class Film {
    private int id;
    private String name;
    private LocalDate releaseDate;
    private String description;
    private Integer duration;
    private Set<Genre> genres;
    private Rating mpa;
    private Set<Director> directors;


    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("release_date", java.sql.Date.valueOf(releaseDate));
        values.put("description", description);
        values.put("duration", duration);
        values.put("rating_id", mpa.getId());
        return values;
    }
}
