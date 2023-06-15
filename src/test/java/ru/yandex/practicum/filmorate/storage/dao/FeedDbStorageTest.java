package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.storage.FeedStorage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@SqlGroup({
        @Sql(scripts = "/test/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FeedDbStorageTest {

    private final FeedStorage feedStorage;

    @Test
    void feedStorageTest() {
        feedStorage.create(1, 2, "FRIEND", "ADD");
        feedStorage.create(1, 5, "FRIEND", "ADD");
        feedStorage.create(1, 1, "LIKE", "ADD");
        feedStorage.create(1, 2, "LIKE", "ADD");
        feedStorage.create(1, 3, "LIKE", "ADD");
        feedStorage.create(1, 4, "LIKE", "ADD");
        feedStorage.create(1, 1, "REVIEW", "ADD");

        System.out.println(feedStorage.getByUserId(1));
        assertThat(feedStorage.getByUserId(1), hasSize(7));
    }
}
