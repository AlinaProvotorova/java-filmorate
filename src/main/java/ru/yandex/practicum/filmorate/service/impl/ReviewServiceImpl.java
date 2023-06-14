package ru.yandex.practicum.filmorate.service.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.validators.ReviewValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;

import static ru.yandex.practicum.filmorate.validators.Constants.*;

@Service
@Data
public class ReviewServiceImpl implements ReviewService {

    private final ReviewStorage reviewStorage;
    private final FilmStorage filmDbStorage;
    private final UserStorage userDbStorage;

    public ReviewServiceImpl(ReviewStorage reviewStorage, FilmStorage filmDbStorage, UserStorage userDbStorage) {
        this.reviewStorage = reviewStorage;
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
    }


    @Override
    public Review create(Review review) {
        ReviewValidate.validateReview(review);
        ReviewValidate.validateId(review.getUserId());
        ReviewValidate.validateId(review.getFilmId());
        userDbStorage.getById(review.getUserId()).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, review.getUserId())));
        filmDbStorage.getById(review.getFilmId()).orElseThrow(
                () -> new NotFoundException(String.format(FILM_NOT_FOUND, review.getFilmId())));

        return reviewStorage.create(review);
    }

    @Override
    public Review getReviewById(Integer id) {
        return reviewStorage.getReviewById(id).orElseThrow(
                () -> new NotFoundException(String.format(REVIEW_NOT_FOUND, id))
        );
    }

    @Override
    public List<Review> getReviewByFilmId(Integer filmId, Integer count) {

        if (filmId == null) {
            return reviewStorage.getAllReviews(count);
        } else  {
            ReviewValidate.validateId(filmId);

            if (filmDbStorage.getById(filmId).isPresent()) {
                return reviewStorage.getReviewByFilmId(filmId, count);
            } else {
                throw new NotFoundException(String.format(FILM_NOT_FOUND, filmId));
            }
        }
    }

    @Override
    public Review update(Review review) {
        ReviewValidate.validateId(review.getReviewId());

        return reviewStorage.update(review).orElseThrow( () -> new NotFoundException
                (String.format(REVIEW_NOT_FOUND, review.getReviewId())));
    }

    @Override
    public void addLike(Integer reviewId, Integer userId) {
        ReviewValidate.validateId(reviewId);
        getReviewById(reviewId);

        UserValidate.validateId(userId);
        userDbStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));

        reviewStorage.addLike(reviewId, userId);
    }

    @Override
    public void addDislike(Integer reviewId, Integer userId) {
        ReviewValidate.validateId(reviewId);
        getReviewById(reviewId);

        UserValidate.validateId(userId);
        userDbStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));


        reviewStorage.addDisLike(reviewId, userId);
    }

    @Override
    public void deleteLike(Integer reviewId, Integer userId) {
        ReviewValidate.validateId(reviewId);
        getReviewById(reviewId);

        UserValidate.validateId(userId);
        userDbStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));

        reviewStorage.deleteLike(reviewId, userId);
    }

    @Override
    public void deleteDislike(Integer reviewId, Integer userId) {
        ReviewValidate.validateId(reviewId);
        getReviewById(reviewId);

        UserValidate.validateId(userId);
        userDbStorage.getById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND, userId)));

        reviewStorage.deleteDisLike(reviewId,userId);
    }

    @Override
    public void deleteReviewById(Integer id) {
        ReviewValidate.validateId(id);
        getReviewById(id);

        reviewStorage.deleteReviewById(id);
    }
}