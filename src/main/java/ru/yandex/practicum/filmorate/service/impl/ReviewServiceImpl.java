package ru.yandex.practicum.filmorate.service.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.FeedStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
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
    private final FeedStorage feedStorage;

    @Autowired
    public ReviewServiceImpl(ReviewStorage reviewStorage,
                             FilmStorage filmDbStorage,
                             UserStorage userDbStorage,
                             FeedStorage feedStorage) {
        this.reviewStorage = reviewStorage;
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
        this.feedStorage = feedStorage;
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
        Review reviewWithId = reviewStorage.create(review);
        feedStorage.create(review.getUserId(), reviewWithId.getReviewId(), EventType.REVIEW, EventOperation.ADD);
        return reviewWithId;
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
        } else {
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
        Review reviewWithId = getReviewById(review.getReviewId());
        feedStorage.create(reviewWithId.getUserId(),
                reviewWithId.getReviewId(),
                EventType.REVIEW,
                EventOperation.UPDATE);
        return reviewStorage.update(review).orElseThrow(() -> new NotFoundException(
                String.format(REVIEW_NOT_FOUND, review.getReviewId()))
        );
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

        reviewStorage.deleteDisLike(reviewId, userId);
    }

    @Override
    public void deleteReviewById(Integer id) {
        ReviewValidate.validateId(id);
        Review review = getReviewById(id);

        feedStorage.create(review.getUserId(), review.getReviewId(), EventType.REVIEW, EventOperation.REMOVE);
        reviewStorage.deleteReviewById(id);
    }
}
