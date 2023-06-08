package ru.yandex.practicum.filmorate.service.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.List;

@Service
@Data
public class ReviewServiceImpl implements ReviewService {

    private final ReviewStorage reviewStorage;

    public ReviewServiceImpl(ReviewStorage reviewStorage) {
        this.reviewStorage = reviewStorage;
    }


    @Override
    public Review create(Review review) {
        return reviewStorage.create(review);
    }

    @Override
    public Review getReviewById(Integer id) {
        return reviewStorage.getReviewById(id);
    }

    @Override
    public List<Review> getReviewByFilmId(Integer filmId, Integer count) {
        return reviewStorage.getReviewByFilmId(filmId, count);
    }

    @Override
    public Review update(Review review) {
        return reviewStorage.update(review);
    }

    @Override
    public Review addLike(Integer id, Integer userId) {
        return reviewStorage.addLike(id, userId);
    }

    @Override
    public Review addDislike(Integer id, Integer userId) {
        return reviewStorage.addDisLike(id, userId);
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        reviewStorage.deleteLike(id, userId);

    }

    @Override
    public void deleteDislike(Integer id, Integer userId) {
        reviewStorage.deleteDisLike(id,userId);

    }

    @Override
    public String deleteReviewById(Integer id) {
        return reviewStorage.deleteReviewById(id);
    }
}
