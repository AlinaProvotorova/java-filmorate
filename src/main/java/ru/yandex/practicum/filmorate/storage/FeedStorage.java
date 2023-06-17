package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.EventOperation;

import java.util.List;

public interface FeedStorage {

    void create(Integer userId, Integer entityId, EventType eventType, EventOperation operation);

    List<Event> getByUserId(Integer userId);
}
