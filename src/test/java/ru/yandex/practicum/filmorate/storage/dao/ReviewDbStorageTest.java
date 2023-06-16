package ru.yandex.practicum.filmorate.storage.dao;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewDbStorageTest {

    private final ReviewDbStorage reviewDbStorage;

    private Review testReview;

    @BeforeEach
    void setUp() {
        testReview = Review.builder()
                .content("This film is soo bad.")
                .isPositive(false)
                .userId(1)
                .filmId(1)
                .build();

    }


    @Test
    void testCreateReview() {
        Review newReview = reviewDbStorage.create(testReview);
        assertEquals(testReview, newReview);
    }

    @Test
    void testShowAllReviews() {
        reviewDbStorage.create(testReview);
        List<Review> reviewList = reviewDbStorage.getAllReviews(1);

        assertEquals(List.of(testReview), reviewList);
    }

    @Test
    void testGetReviewById() {
        Review review = reviewDbStorage.create(testReview);
        Optional<Review> reviewFromDataBase = reviewDbStorage.getReviewById(1);

        assertEquals(Optional.of(review), reviewFromDataBase);
    }

    @Test
    void updateReview() {
        Review review = reviewDbStorage.create(testReview);
        review.setContent("NewContent");

        Optional<Review> updatedReview = reviewDbStorage.update(review);

        assertEquals(Optional.of(review), updatedReview);
    }

    @Test
    void deleteReviewById() {
        reviewDbStorage.create(testReview);

        assertEquals(reviewDbStorage.getAllReviews(1).size(), 1);

        reviewDbStorage.deleteReviewById(1);
        assertEquals(reviewDbStorage.getAllReviews(1).size(), 0);
    }
}
