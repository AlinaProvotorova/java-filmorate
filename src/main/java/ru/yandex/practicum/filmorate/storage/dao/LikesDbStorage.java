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
    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        String sql = "SELECT ff.id, ff.name, ff.description, ff.release_date, ff.duration, ff.rating_id " +
                "FROM (SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id " +
                "FROM FILM f " +
                "LEFT JOIN FILM_GENRE fg ON fg.film_id = f.id " +
                "WHERE (? IS NULL OR fg.genre_id = COALESCE(?, fg.genre_id)) " +
                "GROUP BY f.id) ff " +
                "LEFT JOIN LIKES_FILM lf ON lf.film_id = ff.id " +
                "WHERE (? IS NULL OR YEAR(ff.release_date) = COALESCE(?, YEAR(ff.release_date))) " +
                "GROUP BY ff.id " +
                "ORDER BY COUNT(lf.user_id) DESC " +
                "LIMIT ?";
        return new ArrayList<>(jdbcTemplate.query(sql,
                filmDbStorage::makeFilm,
                genreId, genreId, year, year, count));
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

    @Override
    public List<Film> getCommonFilms(Integer userId, Integer friendId) {
        List<Film> userFilms = getFavoriteFilmsByUser(userId);
        List<Film> friendFilms = getFavoriteFilmsByUser(friendId);
        userFilms.retainAll(friendFilms);
        return userFilms;
    }
}
