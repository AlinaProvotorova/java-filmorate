package ru.yandex.practicum.filmorate.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidateTest {

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .name("")
                .login("Alina")
                .email("Alina@mail")
                .birthday(LocalDate.of(1999, 12, 12))
                .build();
    }

    @Test
    void validateEmail() {
        user.setEmail("");
        assertThrows(
                ValidationException.class,
                () -> UserValidate.validateUser(user),
                "Электронная почта не может быть пустой");
        user.setEmail("a");
        assertThrows(
                ValidationException.class,
                () -> UserValidate.validateUser(user),
                "Электронная почта должна содержать символ @");
    }

    @Test
    void validateLogin() {
        user.setLogin("");
        assertThrows(
                ValidationException.class,
                () -> UserValidate.validateUser(user),
                "Логин не может быть пустым");
        user.setLogin("Alina ");
        assertThrows(
                ValidationException.class,
                () -> UserValidate.validateUser(user),
                "Логин не может содержать пробелы");
    }

    @Test
    void validateName() {
        UserValidate.validateUser(user);
        assertEquals(
                user.getLogin(),
                user.getName(),
                "Имя должно быть установленно значением Логина");
    }

    @Test
    void validateBirthday() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(
                ValidationException.class,
                () -> UserValidate.validateUser(user),
                "Дата рождения не может быть в будущем.");
    }

}