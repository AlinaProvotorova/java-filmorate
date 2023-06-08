package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewStorage {

    Review create(Review review);

    Review getReviewById(Integer id);

    List<Review> getReviewByFilmId(Integer filmId, Integer count);

    Review update(Review review);

    Review addLike(Integer id, Integer userId);

    Review addDisLike(Integer id, Integer userId);

    void deleteLike(Integer id, Integer userId);

    void deleteDisLike(Integer id, Integer userId);

    String deleteReviewById(Integer id);
}
