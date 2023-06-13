package ru.yandex.practicum.filmorate.service.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidate;
import ru.yandex.practicum.filmorate.validators.ReviewValidate;
import ru.yandex.practicum.filmorate.validators.UserValidate;

import java.util.List;

import static ru.yandex.practicum.filmorate.validators.Constants.FILM_NOT_FOUND;
import static ru.yandex.practicum.filmorate.validators.Constants.REVIEW_NOT_FOUND;

@Service
@Data
public class ReviewServiceImpl implements ReviewService {

    private final ReviewStorage reviewStorage;

    public ReviewServiceImpl(ReviewStorage reviewStorage) {
        this.reviewStorage = reviewStorage;
    }


    @Override
    public Review create(Review review) {
        ReviewValidate.validateReview(review);
        ReviewValidate.validateId(review.getUserId());
        ReviewValidate.validateId(review.getFilmId());
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
            
            return  reviewStorage.getReviewByFilmId(filmId, count).orElseThrow(
                    () -> new NotFoundException(String.format(FILM_NOT_FOUND, filmId)));
        }
    }

    @Override
    public Review update(Review review) {
        ReviewValidate.validateId(review.getReviewId());

        return reviewStorage.update(review).orElseThrow( () -> new NotFoundException
                (String.format(REVIEW_NOT_FOUND, review.getReviewId())));
    }

    @Override
    public void addLike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        reviewStorage.addLike(id, userId);
    }

    @Override
    public void addDislike(Integer id, Integer userId) {
        ReviewValidate.validateId(id);
        UserValidate.validateId(userId);

        reviewStorage.addDisLike(id, userId);
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
    public void deleteReviewById(Integer id) {
        ReviewValidate.validateId(id);

        reviewStorage.deleteReviewById(id);
    }
}
