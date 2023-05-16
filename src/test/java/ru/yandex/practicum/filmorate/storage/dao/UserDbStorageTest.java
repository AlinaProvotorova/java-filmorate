package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userStorage;


    @Test
    void getById() {
        Optional<User> userOptional = userStorage.getById(1);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getAll() {
        List<User> allUsers = userStorage.getAll();
        assertThat(allUsers)
                .hasSize(5);
    }

    @Test
    void create() {
        User user = userStorage.create(User.builder()
                .name("")
                .login("Alina")
                .email("Alina@mail")
                .birthday(LocalDate.of(1999, 12, 12))
                .build());
        assertThat(user)
                .hasFieldOrPropertyWithValue("login", "Alina");
    }

    @Test
    void update() {
        User user1 = User.builder()
                .id(1)
                .name("newName")
                .login("Alina")
                .email("alina@y.ru")
                .birthday(LocalDate.of(1997, 1, 14))
                .build();
        Optional<User> userOptional = userStorage.update(user1);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "newName")
                );
    }

    @Test
    void delete() {
        userStorage.delete(1);
        Optional<User> userOptional = userStorage.getById(1);
        assertThat(userOptional)
                .isEmpty();
    }

}