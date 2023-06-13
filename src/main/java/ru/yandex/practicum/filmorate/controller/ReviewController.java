package ru.yandex.practicum.filmorate.controller;


import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    POST /reviews
//    Добавление нового отзыва.
    @PostMapping
    public Review create(@Valid @RequestBody Review review) {
        return reviewService.create(review);
    }

//    GET /reviews/{id}
//    Получение отзыва по идентификатору.
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Integer id) {
        return reviewService.getReviewById(id);
    }

//    GET /reviews?filmId={filmId}&count={count}
//    Получение всех отзывов по идентификатору фильма, если фильм не указан то все. Если кол-во не указано то 10.
    @GetMapping
    public List<Review> getReviewByFilmId(@RequestParam(required = false) Integer filmId,
                                          @RequestParam(required = false, defaultValue = "10") Integer count) {
        return reviewService.getReviewByFilmId(filmId, count);
    }

//    PUT /reviews
//    Редактирование уже имеющегося отзыва.
    @PutMapping
    public Review update(@Valid @RequestBody Review review) {
        return reviewService.update(review);
    }

    //    PUT /reviews/{id}/like/{userId} — пользователь ставит лайк отзыву.
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        reviewService.addLike(id, userId);
    }

    //    PUT /reviews/{id}/dislike/{userId} — пользователь ставит дизлайк отзыву.
    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        reviewService.addDislike(id, userId);
    }

    //    DELETE /reviews/{id}/like/{userId} — пользователь удаляет лайк/дизлайк отзыву.
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        reviewService.deleteLike(id, userId);
    }

    //    DELETE /reviews/{id}/dislike/{userId} — пользователь удаляет дизлайк отзыву.
    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislike(@PathVariable Integer id, @PathVariable Integer userId) {
        reviewService.deleteDislike(id, userId);
    }

//    DELETE /reviews/{id}
//    Удаление уже имеющегося отзыва.
    @DeleteMapping("/{id}")
    public String deleteReviewById(@PathVariable Integer id) {
        reviewService.deleteReviewById(id);
        return "Фильм " + id + " удален";
    }

}
