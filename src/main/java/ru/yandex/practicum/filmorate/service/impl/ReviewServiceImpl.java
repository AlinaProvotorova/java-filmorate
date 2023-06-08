package ru.yandex.practicum.filmorate.service.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.ReviewValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

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
        ReviewValidate.validateId(review.getReviewId());

        return reviewStorage.create(review);
    }

    @Override
    public Review getReviewById(Integer id) {
        ReviewValidate.validateId(id);

        return reviewStorage.getReviewById(id);
    }

    @Override
    public List<Review> getReviewByFilmId(Integer filmId, Integer count) {
        FilmValidate.validateId(filmId);

        return reviewStorage.getReviewByFilmId(filmId, count);
    }

    @Override
    public Review update(Review review) {
        ReviewValidate.validateId(review.getReviewId());

        return reviewStorage.update(review);
    }

    @Override
    public Review addLike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        return reviewStorage.addLike(id, userId);
    }

    @Override
    public Review addDislike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        return reviewStorage.addDisLike(id, userId);
    }

    @Override
    public void deleteLike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        reviewStorage.deleteLike(id, userId);

    }

    @Override
    public void deleteDislike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        reviewStorage.deleteDisLike(id,userId);

    }

    @Override
    public String deleteReviewById(Integer id) {
        ReviewValidate.validateId(id);

        return reviewStorage.deleteReviewById(id);
    }
}
