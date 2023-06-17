package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class Event {
    private int eventId;
    private int userId;
    private int entityId;
    private Timestamp timestamp;
    private EventType eventType;
    private EventOperation operation;

    @JsonGetter("timestamp")
    public Long getTimestamp() {
        return timestamp.getTime();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", userId);
        values.put("entity_id", entityId);
        values.put("timestamp", timestamp);
        values.put("event_type", eventType.getName());
        values.put("operation", operation.getName());
        return values;
    }
}
