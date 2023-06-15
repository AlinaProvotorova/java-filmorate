package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Review {

    private Integer reviewId;
    private String content;
    private Boolean isPositive;
    private final Integer userId;
    private final Integer filmId;
    private Integer useful;


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
