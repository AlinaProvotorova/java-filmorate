package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum EventOperation {
    ADD("ADD"),
    REMOVE("REMOVE"),
    UPDATE("UPDATE");

    private final String name;

    EventOperation(String name) {
        this.name = name;
    }
}

