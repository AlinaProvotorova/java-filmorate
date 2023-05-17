package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Component
@Qualifier("filmDbStorage")
@Slf4j
public class FilmDbStorage implements FilmStorage {

    protected final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;


    @Override
    public List<Film> getAll() {
        String sql = "select * from film";
        return jdbcTemplate.query(sql, this::makeFilm);

    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("id");
        Integer id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(
                        "insert into film_genre (film_id, genre_id) VALUES (?, ?)",
                        id, genre.getId()
                );
            }
        }
        return getById(id).isPresent() ? getById(id).get() : film;
    }

    @Override
    public Optional<Film> update(Film film) {
        if (getById(film.getId()).isPresent()) {
            jdbcTemplate.update(
                    "update film set " +
                            "name=?, release_date=?, description=?, duration=?, rating_id=? " +
                            "where id = ?",
                    film.getName(),
                    film.getReleaseDate(),
                    film.getDescription(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId()
            );
            if (film.getGenres() != null) {
                jdbcTemplate.update(
                        "DELETE FROM film_genre WHERE film_id = ? and genre_id",
                        film.getId()
                );
                if (!film.getGenres().isEmpty()) {
                    for (Genre genre : film.getGenres()) {
                        jdbcTemplate.update(
                                "insert into film_genre (film_id, genre_id) " +
                                        "VALUES (?, ?)",
                                film.getId(), genre.getId()
                        );
                    }
                }
            }
            return getById(film.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM film_genre WHERE film_id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Optional<Film> getById(Integer id) {
        try {
            Film film = jdbcTemplate.queryForObject(
                    "select * from film where id = ?",
                    this::makeFilm, id
            );
            return Optional.ofNullable(film);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }


    protected Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .genres(genreStorage.getGenresByFilm(rs.getInt("id")))
                .mpa(ratingStorage.getById(
                        rs.getInt("rating_id")).orElse(new Rating())
                )
                .build();
    }
}