package ru.yandex.practicum.filmorate.storage.dao;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.util.List;
import java.util.Optional;

@Component
@Qualifier("likesDbStorage")
@Slf4j
@Data
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;

    @Override
    public Optional<Film> like(Integer filmId, Integer userId) {
        String sql = "INSERT INTO likes_film (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                filmId,
                userId
        );
        return filmDbStorage.getById(filmId);
    }

    @Override
    public Optional<Film> dislike(Integer filmId, Integer userId) {
        String sql = "delete from likes_film where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
        return filmDbStorage.getById(filmId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count) {
        String sql = "SELECT f.ID, f.NAME, f.RELEASE_DATE, f.DESCRIPTION, f.DURATION, f.RATING_ID " +
                "FROM FILM f " +
                "LEFT JOIN LIKES_FILM lf ON lf.FILM_ID=f.ID " +
                "GROUP BY f.ID " +
                "ORDER BY COUNT(LF.USER_ID) DESC LIMIT ?";
        return jdbcTemplate.query(sql, filmDbStorage::makeFilm, count);
    }
}
