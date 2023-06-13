package ru.yandex.practicum.filmorate.storage.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.validators.ReviewValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

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
        ReviewValidate.validateId(review.getUserId());
        ReviewValidate.validateId(review.getFilmId());

        log.info("Получен запрос на создание отзыва");

        String sql = "INSERT INTO reviews (content, is_positive, user_id, film_id) " +
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
        String sql = "SELECT reviews.*, COALESCE(SUM(review_likes.useful), 0) AS useful FROM reviews " +
                "LEFT JOIN review_likes ON reviews.id = review_likes.review_id " +
                "GROUP BY reviews.id " +
                "HAVING reviews.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, reviewMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Отзыва с id= " + " нет  в базе");
        }
    }

    @Override
    public List<Review> getReviewByFilmId(Integer filmId, Integer count) {
        if (filmId == null) {
            String sql = "SELECT reviews.*, COALESCE(SUM(review_likes.useful), 0) AS useful " +
                    "FROM  reviews " +
                    "LEFT JOIN review_likes ON reviews.id = review_likes.review_id " +
                    "GROUP BY reviews.ID " +
                    "ORDER BY useful DESC " +
                    "LIMIT ? ";
            return jdbcTemplate.query(sql, reviewMapper, count);
        } else {
            String sql = "SELECT reviews.*, SUM(review_likes.useful) AS useful " +
                    "FROM  reviews " +
                    "LEFT JOIN review_likes ON reviews.id = review_likes.review_id " +
                    "GROUP BY reviews.ID " +
                    "HAVING film_id = ? " +
                    "ORDER BY useful DESC " +
                    "LIMIT ?";
            return jdbcTemplate.query(sql, reviewMapper, filmId, count);

        }

    }

    @Override
    public Review update(Review review) {
        log.info("Обновление отзыва");

        String sql = "UPDATE reviews SET content = ?, is_positive = ? " +
                "WHERE id= ?";

        int count = jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(),
                review.getReviewId());
        if (count != 1) {
            throw new NotFoundException("Отзыва с id= " + review.getReviewId() + " нет  в базе");
        }

        log.info("Отзыв обновлен");

        return getReviewById(review.getReviewId());
    }

    @Override
    public void addLike(Integer id, Integer userId) {
        String sql = "INSERT INTO review_likes (review_id, user_id, useful) VALUES (?, ?, ?)";
        int count = jdbcTemplate.update(sql, id, userId, 1);

        if (count != 1) {
            throw new NotFoundException("Отзыва с id= " + id + " нет  в базе");
        }
    }

    @Override
    public void addDisLike(Integer id, Integer userId) {
        String sql = "INSERT INTO review_likes (review_id, user_id, useful) VALUES (?, ?, ?)";
        int count = jdbcTemplate.update(sql, id, userId, -1);

        if (count != 1) {
            throw new NotFoundException("Отзыва с id= " + id + " нет  в базе");
        }
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        String sql = "DELETE FROM review_likes WHERE review_id = ? AND user_id = ?";

        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void deleteDisLike(Integer id, Integer userId) {
        String sql = "DELETE FROM review_likes WHERE review_id = ? AND user_id = ?";

        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void deleteReviewById(Integer id) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
