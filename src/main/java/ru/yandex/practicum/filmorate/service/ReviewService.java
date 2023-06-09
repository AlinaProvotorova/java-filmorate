package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {

    Review create(Review review);

    Review getReviewById(Integer id);

    List<Review> getReviewByFilmId(Integer filmId, Integer count);

    Review update(Review review);

    void addLike(Integer id, Integer userId);

    void addDislike(Integer id, Integer userId);

    void deleteLike(Integer id, Integer userId);

    void deleteDislike(Integer id, Integer userId);

    void deleteReviewById(Integer id);

}
