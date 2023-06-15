package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Event;

import java.sql.Timestamp;
import java.util.List;

public interface FeedStorage {

    void create(Integer userId, Integer entityId, String eventType, String operation);

    List<Event> getByUserId(Integer userId);
}
