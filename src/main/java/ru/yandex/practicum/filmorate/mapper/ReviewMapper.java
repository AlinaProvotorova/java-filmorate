package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String content =rs.getString("content");
        Boolean isPositive = rs.getBoolean("isPositive");
        Integer userId = rs.getInt("user_id");
        Integer filmId = rs.getInt("film_id");;
        Integer useful = null;


        return new Review(id,content,isPositive,userId,filmId,useful);
    }
}
