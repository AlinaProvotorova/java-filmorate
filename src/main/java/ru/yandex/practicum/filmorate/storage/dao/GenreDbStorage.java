package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Primary
@RequiredArgsConstructor
@Component
@Qualifier("genreDbStorage")
@Slf4j
public class GenreDbStorage implements GenreStorage {

    protected final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        String sql = "select id, name from genre";
        return jdbcTemplate.query(sql, this::makeGenre);
    }

    @Override
    public Set<Genre> getGenresByFilm(Integer filmId) {
        String sql = "SELECT g.ID, g.NAME  FROM GENRE g JOIN FILM_GENRE fg ON g.ID = fg.GENRE_ID WHERE FG.FILM_ID = ? ORDER BY g.ID";
        return new HashSet<>(jdbcTemplate.query(sql, this::makeGenre, filmId));
    }

    @Override
    public Genre create(Genre genre) {
        jdbcTemplate.update(
                "INSERT INTO genre " +
                        "(name) " +
                        "VALUES (?)",
                genre.getName()
        );
        return genre;
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        if (getById(genre.getId()).isPresent()) {
            jdbcTemplate.update(
                    "update genre set " +
                            "name=? " +
                            "where id = ?",
                    genre.getName(),
                    genre.getId()

            );
            return getById(genre.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from genre where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Optional<Genre> getById(Integer id) {
        try {
            String sql = "select * from genre where id = ?";
            Genre genre = jdbcTemplate.queryForObject(sql, this::makeGenre, id);
            return Optional.ofNullable(genre);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    protected Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
