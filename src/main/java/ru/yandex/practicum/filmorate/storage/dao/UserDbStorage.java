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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Component
@Qualifier("userDbStorage")
@Slf4j
public class UserDbStorage implements UserStorage {
    protected final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmStorage;


    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, this::makeUser);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Integer id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        return getById(id).isPresent() ? getById(id).get() : user;
    }

    @Override
    public Optional<User> update(User user) {
        if (getById(user.getId()).isPresent()) {
            jdbcTemplate.update(
                    "update users set login = ?, email = ?, name = ?, birthday = ? " +
                            "where id = ?",
                    user.getLogin(),
                    user.getEmail(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId()
            );
            return getById(user.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "delete from users where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Optional<User> getById(Integer id) {
        try {
            User user = jdbcTemplate.queryForObject(
                    "select * from users where id = ?",
                    this::makeUser, id
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> recommendations(Integer id) {
        String sql = "SELECT f.* FROM film f JOIN (" +
                "SELECT DISTINCT lf.FILM_ID FROM LIKES_FILM lf WHERE lf.USER_ID = (" +
                "SELECT lf2.USER_ID FROM LIKES_FILM lf2 WHERE lf2.USER_ID <> ? AND lf2.FILM_ID IN (" +
                "SELECT FILM_ID FROM LIKES_FILM WHERE USER_ID = ?) " +
                "GROUP BY lf2.USER_ID ORDER BY COUNT(*) DESC LIMIT 1) AND lf.FILM_ID NOT IN (" +
                "SELECT FILM_ID FROM LIKES_FILM WHERE USER_ID = ?)" +
                ") lf ON lf.FILM_ID = f.ID ";
        return jdbcTemplate.query(sql, filmStorage::makeFilm, id, id, id);
    }


    protected User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .birthday(Objects.requireNonNull(rs.getDate("birthday")).toLocalDate())
                .build();
    }
}
