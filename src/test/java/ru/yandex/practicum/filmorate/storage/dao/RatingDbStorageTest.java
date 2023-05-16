package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RatingDbStorageTest {
    private final RatingDbStorage ratingDbStorage;

    @Test
    void getAll() {
        List<Rating> films = ratingDbStorage.getAll();
        assertThat(films).hasSize(6);
    }

    @Test
    void create() {
        Rating rating = ratingDbStorage.create(Rating.builder()
                .name("Новый рейтинг")
                .description("Описание")
                .build());
        assertThat(rating)
                .hasFieldOrPropertyWithValue("name", "Новый рейтинг");
    }

    @Test
    void update() {
        Rating rating = Rating.builder()
                .id(1)
                .name("ППП")
                .description("Описание")
                .build();
        assertThat(ratingDbStorage.update(rating))
                .isPresent()
                .hasValueSatisfying(r ->
                        assertThat(r)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "ППП")
                );
    }


    @Test
    void getById() {
        Optional<Rating> ratingOptional = ratingDbStorage.getById(1);
        assertThat(ratingOptional)
                .isPresent()
                .hasValueSatisfying(rating ->
                        assertThat(rating).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}