package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Component
@Qualifier("ratingDbStorage")
@Slf4j
public class RatingDbStorage implements RatingStorage {

    protected final JdbcTemplate jdbcTemplate;

    @Override
    public List<Rating> getAll() {
        String sql = "select * from rating";
        return jdbcTemplate.query(sql, this::makeRating);

    }

    @Override
    public Rating create(Rating rating) {
        jdbcTemplate.update(
                "INSERT INTO rating " +
                        "(name, description) " +
                        "VALUES (?, ?)",
                rating.getName(),
                rating.getDescription()
        );
        return rating;
    }

    @Override
    public Optional<Rating> update(Rating rating) {
        if (getById(rating.getId()).isPresent()) {
            jdbcTemplate.update(
                    "update rating set " +
                            "name=?, description=? " +
                            "where id = ?",
                    rating.getName(),
                    rating.getDescription(),
                    rating.getId()
            );
            return getById(rating.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from rating where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Optional<Rating> getById(Integer id) {
        try {
            String sql = "select * from rating where id = ?";
            Rating rating = jdbcTemplate.queryForObject(sql, this::makeRating, id);
            return Optional.ofNullable(rating);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    protected Rating makeRating(ResultSet rs, int rowNum) throws SQLException {
        return Rating.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
