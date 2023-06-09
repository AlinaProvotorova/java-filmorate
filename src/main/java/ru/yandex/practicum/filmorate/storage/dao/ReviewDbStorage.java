package ru.yandex.practicum.filmorate.storage.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.validators.ReviewValidate;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Primary
@Slf4j
public class ReviewDbStorage implements ReviewStorage {

    private final JdbcTemplate jdbcTemplate;
    private final ReviewMapper reviewMapper;

    public ReviewDbStorage(JdbcTemplate jdbcTemplate, ReviewMapper reviewMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reviewMapper = reviewMapper;
    }


    @Override
    public Review create(Review review) {
        ReviewValidate.validateReview(review);
        log.info("Получен запрос на создание отзыва");

        String sql = "INSERT INTO reviews_film (content, is_Positive, user_id, film_id) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(sql, new String[]{"id"});
            prepareStatement.setString(1,review.getContent());
            prepareStatement.setBoolean(2, review.getIsPositive());
            prepareStatement.setInt(3, review.getUserId());
            prepareStatement.setInt(4, review.getFilmId());
            return prepareStatement;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            review.setReviewId(keyHolder.getKey().intValue());
        }

        log.info("Отзыв добавлен в базу");

        return review;
    }

    @Override
    public Review getReviewById(Integer id) {



        return null;
    }

    @Override
    public List<Review> getReviewByFilmId(Integer filmId, Integer count) {
        return null;
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public void addLike(Integer id, Integer userId) {
        String sql = "INSERT INTO reviews_like (review_id, user_id, useful) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, userId, 1);

    }

    @Override
    public void addDisLike(Integer id, Integer userId) {
        String sql = "INSERT INTO reviews_like (review_id, user_id, useful) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, userId, -1);

    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        String sql = "DELETE FROM reviews_like WHERE review_id = ? AND user_id = ?";

        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void deleteDisLike(Integer id, Integer userId) {
        String sql = "DELETE FROM reviews_like WHERE review_id = ? AND user_id = ?";

        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void deleteReviewById(Integer id) {
        String sql = "DELETE FROM reviews_film WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
