package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

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
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;

    @Test
    void getById() {
        Optional<Film> filmOptional = filmDbStorage.getById(1);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    void getAll() {
        List<Film> films = filmDbStorage.getAll();
        assertThat(films).hasSize(5);
    }

    @Test
    void create() {
        Film film = filmDbStorage.create(Film.builder()
                .name("Фильм1")
                .description("Описание")
                .releaseDate(LocalDate.now())
                .duration(60)
                .mpa(Rating.builder().id(1).build())
                .build());
        assertThat(film)
                .hasFieldOrPropertyWithValue("name", "Фильм1");

    }

    @Test
    void update() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм1")
                .description("Описание")
                .releaseDate(LocalDate.now())
                .duration(60)
                .mpa(Rating.builder().id(1).build())
                .build();
        assertThat(filmDbStorage.update(film))
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "Фильм1")
                );
    }

    @Test
    void testGetAllDirectorFilms() {
        List<Film> likes = filmDbStorage.getAllFilmsOfDirector(1, "likes");
        assertThat(likes).hasSize(1);
    }


}