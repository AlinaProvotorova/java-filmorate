package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;
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
    private final DirectorStorage directorStorage;


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

        if (film.getDirectors() != null && !film.getDirectors().isEmpty()) {
            for (Director director : film.getDirectors()) {
                jdbcTemplate.update(
                        "insert into film_directors (film_id, director_id) VALUES (?, ?)",
                        id, director.getId()
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


            jdbcTemplate.update(
                    "DELETE FROM film_directors WHERE film_id = ?",
                    film.getId()
            );
            if (film.getDirectors() != null) {
                if (!film.getDirectors().isEmpty()) {
                    for (Director director : film.getDirectors()) {
                        jdbcTemplate.update(
                                "insert into film_directors (film_id, director_id) " +
                                        "VALUES (?, ?)",
                                film.getId(), director.getId()
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
        String sql = "DELETE FROM film WHERE id = ?";
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

    @Override
    public List<Film> getAllFilmsOfDirector(Integer id, String sortBy) {
        String sortByYear = "select * from film where id in ( " +
                "select film_id from film_directors where director_id = ?) " +
                "order by release_date";

        String sortByLikes = "Select f.* " +
                "from film f join FILM_DIRECTORS FD on f.ID = FD.FILM_ID and fd.DIRECTOR_ID = ? " +
                "left join LIKES_FILM LF on f.ID = LF.FILM_ID " +
                "group by f.ID " +
                "order by count(lf.USER_ID) desc";

        return "likes".equals(sortBy) ? jdbcTemplate.query(sortByLikes, this::makeFilm, id) :
                jdbcTemplate.query(sortByYear, this::makeFilm, id);
    }

    public Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
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
                .directors(directorStorage.getDirectorsByFilm(rs.getInt("id")))
                .build();
    }

    @Override
    public List<Film> search(String query, String by) {

        String select = "SELECT f.* FROM film f " +
                "LEFT JOIN (SELECT film_id, COUNT(user_id) AS likes " +
                "FROM likes_film " +
                "GROUP BY film_id) l ON f.id = l.film_id ";

        String byTitle = "UPPER(f.name) LIKE UPPER(CONCAT('%', ?, '%')) ";

        String joinDirector = "LEFT JOIN film_directors fd ON f.id = fd.film_id " +
                "LEFT JOIN director d ON fd.director_id = d.id ";

        String byDirector = "UPPER(d.name) LIKE UPPER(CONCAT('%', ?, '%')) ";

        String where = "WHERE ";
        String or = "OR ";
        String sortByLikes = "ORDER BY l.likes DESC";

        final String searchByTitle = select + where + byTitle + sortByLikes;
        final String searchByDirector = select + joinDirector + where + byDirector + sortByLikes;
        final String searchBoth = select + joinDirector + where + byDirector + or + byTitle + sortByLikes;

        switch (by) {
            case "director":
                return jdbcTemplate.query(searchByDirector, this::makeFilm, query);
            case "title":
                return jdbcTemplate.query(searchByTitle, this::makeFilm, query);
            case "director,title":
            case "title,director":
                return jdbcTemplate.query(searchBoth, this::makeFilm, query, query);
            default:
                throw new ValidationException("Некорректные параметры запроса.");
        }
    }
}
