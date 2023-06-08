package ru.yandex.practicum.filmorate.storage.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.List;

@Component
@Primary
@Slf4j
public class ReviewDbStorage implements ReviewStorage {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplate;



    public ReviewDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Review create(Review review) {
        return null;
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
    public Review addLike(Integer id, Integer userId) {
        return null;
    }

    @Override
    public Review addDisLike(Integer id, Integer userId) {
        return null;
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {

    }

    @Override
    public void deleteDisLike(Integer id, Integer userId) {

    }

    @Override
    public String deleteReviewById(Integer id) {
        return null;
    }
}
