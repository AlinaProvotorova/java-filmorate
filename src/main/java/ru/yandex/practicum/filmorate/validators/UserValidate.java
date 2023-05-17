package ru.yandex.practicum.filmorate.validators;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.validators.Constants.ID_NOT_POSITIVE;

@Slf4j
@Data
public class UserValidate {

    public static void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().equals("") || !(user.getEmail().contains("@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().equals("") || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
            log.info("Имя установленно значением Логина");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }

    public static void validateId(Integer id) {
        if (id < 0) {
            throw new NotFoundException(ID_NOT_POSITIVE);
        }
    }

    public static boolean validateFriendship(boolean friendship, Integer userId, Integer friendId) {
        validateId(userId);
        validateId(friendId);
        if (!friendship) {
            throw new NotFoundException(String.format("Дружбы между пользователями %s и %s не существует", userId, friendId));
        }
        return friendship;
    }

    public static boolean validateFriendshipRequest(boolean friendship, Integer userId, Integer friendId) {
        validateId(userId);
        validateId(friendId);
        if (!friendship) {
            throw new NotFoundException(String.format("Нельзя подружиться %s и %s не существует", userId, friendId));
        }
        return friendship;
    }
}
