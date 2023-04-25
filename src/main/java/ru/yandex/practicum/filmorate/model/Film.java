package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@AllArgsConstructor
@Builder
@Data
public class Film {
    private int id;
    private String name;
    private LocalDate releaseDate;
    private String description;
    private Integer duration;
    private final Set<Integer> likes = new TreeSet<>();

    public void addLikes(Integer idUser) {
        likes.add(idUser);
    }

    public void delLikes(Integer idUser) {
        likes.remove(idUser);
    }

    public Integer getLenLikes() {
        return likes.size();
    }
}
