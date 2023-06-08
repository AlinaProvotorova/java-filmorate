package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Review {

    private final Integer reviewId;
    private String content;
    private Boolean isPositive;
    private final Integer userId;
    private final Integer filmId;
    private final Integer useful = 0;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", content='" + content + '\'' +
                ", isPositive=" + isPositive +
                ", userId=" + userId +
                ", filmId=" + filmId +
                ", useful=" + useful +
                '}';
    }

    //    {
//        "reviewId": 123,
//            "content": "This film is sooo baad.",
//            "isPositive": false,
//            "userId": 123, // Пользователь
//            "filmId": 2, // Фильм
//            "useful": 20 // рейтинг полезности
//    }

}
