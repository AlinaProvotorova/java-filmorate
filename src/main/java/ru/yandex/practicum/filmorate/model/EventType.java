package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum EventType {
    LIKE("LIKE"),
    FRIEND("FRIEND"),
    REVIEW("REVIEW");

    private final String name;

    EventType(String name) {
        this.name = name;
    }
}