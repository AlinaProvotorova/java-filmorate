package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("likesDbStorage")
@Slf4j
@RequiredArgsConstructor
public class LikesDbStorage implements LikesStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

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

    @Override
    public List<User> getLikesByFilm(Integer filmId) {
        String sql = "SELECT u.ID, u.login, u.email, u.name, u.birthday " +
                "FROM USERS u JOIN likes_film l ON u.ID = l.USER_ID " +
                "WHERE l.FILM_ID = ? ORDER BY u.ID";
        return new ArrayList<>(jdbcTemplate.query(sql, userDbStorage::makeUser, filmId));
    }

    @Override
    public List<Film> getFavoriteFilmsByUser(Integer userId) {
        String sql = "SELECT f.ID, f.name, f.description, f.release_date, f.duration, f.rating_id " +
                "FROM FILM f JOIN likes_film l ON f.ID = l.FILM_ID WHERE l.USER_ID = ? ORDER BY f.ID";
        return new ArrayList<>(jdbcTemplate.query(sql, filmDbStorage::makeFilm, userId));
    }
}
