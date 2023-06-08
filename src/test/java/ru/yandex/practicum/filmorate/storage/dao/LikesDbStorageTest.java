package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikesDbStorageTest {
    private final LikesDbStorage likesDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    void like() {
        likesDbStorage.like(2, 3);
        List<Film> likeFilms = likesDbStorage.getFavoriteFilmsByUser(3);
        List<User> likeUsers = likesDbStorage.getLikesByFilm(2);
        assertThat(likeFilms).hasSize(4);
        assertThat(likeUsers).hasSize(3);

    }

    @Test
    void dislike() {
        likesDbStorage.dislike(4, 1);
        List<Film> likeFilms = likesDbStorage.getFavoriteFilmsByUser(1);
        List<User> likeUsers = likesDbStorage.getLikesByFilm(4);
        assertThat(likeFilms).hasSize(4);
        assertThat(likeUsers).hasSize(1);
    }

    @Test
    void getPopularFilms() {
        List<Film> popularFilms = likesDbStorage.getPopularFilms(2);
        System.out.println(popularFilms);
        assertThat(popularFilms).hasSize(2);
        assertThat(popularFilms).contains(filmDbStorage.getById(1).get());
        assertThat(popularFilms).contains(filmDbStorage.getById(5).get());
    }

    @Test
    void getCommonFilms() {
        List<Film> commonFilms = likesDbStorage.getCommonFilms(1, 3);
        System.out.println(commonFilms);
        assertThat(commonFilms).hasSize(3);
        assertThat(commonFilms).contains(filmDbStorage.getById(1).get());
        assertThat(commonFilms).contains(filmDbStorage.getById(5).get());
        assertThat(commonFilms).contains(filmDbStorage.getById(3).get());
    }
}