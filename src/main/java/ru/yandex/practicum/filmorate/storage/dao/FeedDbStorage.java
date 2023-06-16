package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.storage.FeedStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class FeedDbStorage implements FeedStorage {

    protected final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Integer userId, Integer entityId, EventType eventType, EventOperation operation) {
        Event event = Event.builder()
                .userId(userId)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .eventType(eventType)
                .operation(operation)
                .entityId(entityId)
                .build();
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("event_feed")
                .usingGeneratedKeyColumns("id");
        simpleJdbcInsert.execute(event.toMap());
    }

    @Override
    public List<Event> getByUserId(Integer userId) {
        String sql = "select * from event_feed where user_id = ?";
        return jdbcTemplate.query(sql, this::makeEvent, userId);
    }

    protected Event makeEvent(ResultSet rs, int rowNum) throws SQLException {
        return Event.builder()
                .eventId(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .timestamp(rs.getTimestamp("timestamp"))
                .eventType(EventType.valueOf(rs.getString("event_type")))
                .operation(EventOperation.valueOf(rs.getString("operation")))
                .entityId(rs.getInt("entity_id"))
                .build();
    }
}
