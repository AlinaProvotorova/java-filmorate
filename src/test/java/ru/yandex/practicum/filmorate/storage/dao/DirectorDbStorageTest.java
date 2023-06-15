package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validators.Constants.DIRECTOR_NOT_FOUND;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
class DirectorDbStorageTest {
    private final DirectorDbStorage directorDbStorage;

    @ParameterizedTest()
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void testFindByIdParam(Integer args) {
        Optional<Director> filmOptional = directorDbStorage.getById(args);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", args));
    }

    @Test
    public void testFindByIdParamWhenIdIsNotCorrect() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            directorDbStorage.getById(10).orElseThrow(() ->
                    new NotFoundException(String.format(DIRECTOR_NOT_FOUND, 10)));
        });
        assertEquals(String.format(DIRECTOR_NOT_FOUND, 10), exception.getMessage());
    }

    @Test
    public void testGetAll() {
        List<Director> directors = directorDbStorage.getAll();
        assertThat(directors).hasSize(6);
    }

    @Test
    public void testCreate() {
        Director newDirector = Director.builder()
                .name("New director")
                .build();
        Director director = directorDbStorage.create(newDirector);
        assertThat(director).hasFieldOrPropertyWithValue("id", 7)
                .hasFieldOrPropertyWithValue("name", "New director");
    }

    @Test
    public void testUpdate() {
        Director director = directorDbStorage.getById(1).get();
        assertThat(director).hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Фрэнк Дарабонт");

        director.setName("Новое имя");
        directorDbStorage.update(director);

        Director updatedDirector = directorDbStorage.getById(1).get();
        assertThat(updatedDirector).hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Новое имя");
    }

    @Test
    public void testDelete() {
        Optional<Director> filmOptional = directorDbStorage.getById(1);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1));

        directorDbStorage.delete(1);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            directorDbStorage.getById(1).orElseThrow(() ->
                    new NotFoundException(String.format(DIRECTOR_NOT_FOUND, 1)));
        });
        assertEquals(String.format(DIRECTOR_NOT_FOUND, 1), exception.getMessage());
    }
}