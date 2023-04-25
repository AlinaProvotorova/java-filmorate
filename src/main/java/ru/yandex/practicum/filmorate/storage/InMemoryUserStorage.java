package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Primary
@Data
@Component
public class InMemoryUserStorage implements UserStorage {
    static int nextId = 0;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User create(User user) {
        user.setId(++nextId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }


    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public User getById(Integer id) {
        return users.get(id);
    }
}
