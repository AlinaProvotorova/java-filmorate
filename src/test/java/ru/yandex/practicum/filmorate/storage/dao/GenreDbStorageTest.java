package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {

    private final GenreDbStorage genreDbStorage;

    @Test
    void getAll() {
        List<Genre> genres = genreDbStorage.getAll();
        assertThat(genres).hasSize(7);
    }

    @Test
    void getGenresByFilm() {
        Set<Genre> genres = genreDbStorage.getGenresByFilm(1);
        assertThat(genres).hasSize(2);
    }

    @Test
    void create() {
        Genre genre = genreDbStorage.create(Genre.builder()
                .name("Какойто жанр")
                .build());
        assertThat(genre)
                .hasFieldOrPropertyWithValue("name", "Какойто жанр");
    }

    @Test
    void update() {
        Genre genre = Genre.builder()
                .id(1)
                .name("Какойто жанр")
                .build();
        assertThat(genreDbStorage.update(genre))
                .isPresent()
                .hasValueSatisfying(g ->
                        assertThat(g)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "Какойто жанр")
                );
    }


    @Test
    void getById() {
        Optional<Genre> genreOptional = genreDbStorage.getById(1);
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }
}