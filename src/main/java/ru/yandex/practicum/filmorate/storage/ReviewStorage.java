package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {

    Review create(Review review);

    Optional<Review> getReviewById(Integer id);

    List<Review> getReviewByFilmId(Integer filmId, Integer count);
    List<Review> getAllReviews(Integer count);

    Optional<Review> update(Review review);

    void addLike(Integer id, Integer userId);

    void addDisLike(Integer id, Integer userId);

    void deleteLike(Integer id, Integer userId);

    void deleteDisLike(Integer id, Integer userId);

    void deleteReviewById(Integer id);

    void deleteAllReviews();
}
