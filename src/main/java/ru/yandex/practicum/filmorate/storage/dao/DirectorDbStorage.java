package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class DirectorDbStorage implements DirectorStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Director> getAll() {
        String sql = "select * from director";
        return jdbcTemplate.query(sql, this::makeDirector);
    }

    @Override
    public Set<Director> getDirectorsByFilm(Integer filmId) {
        String sql = "SELECT d.* FROM DIRECTOR d JOIN FILM_DIRECTORS fd ON d.ID = fd.DIRECTOR_ID " +
                "WHERE fd.FILM_ID = ? " +
                "ORDER BY d.ID";
        return new HashSet<>(jdbcTemplate.query(sql, this::makeDirector, filmId));
    }

    @Override
    public Director create(Director director) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("director")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", director.getName());

        Integer createdId = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();
        director.setId(createdId);

        return director;
    }

    @Override
    public Optional<Director> update(Director director) {
        String sql = "update director set name =? " +
                "where id = ?";
        if (getById(director.getId()).isPresent()) {
            jdbcTemplate.update(sql, director.getName(), director.getId());
            return Optional.of(director);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from director where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Optional<Director> getById(Integer id) {
        String sql = "select * " +
                "from director " +
                "where id = ?";
        List<Director> results = jdbcTemplate.query(sql, this::makeDirector, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    private Director makeDirector(ResultSet rs, int rowNum) throws SQLException {
        return Director.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
