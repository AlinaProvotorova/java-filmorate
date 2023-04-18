package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidate {
    public static String validateEmail = "Электронная почта не может быть пустой и должна содержать символ @";
    public static String validateLogin = "Логин не может быть пустым и содержать пробелы";
    public static String validateName = "Имя установленно значением Логина";
    public static String validateBirthday = "Дата рождения не может быть в будущем.";

    public static void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().equals("") || !(user.getEmail().contains("@"))) {
            log.debug(validateEmail);
            throw new ValidationException(validateEmail);
        }
        if (user.getLogin() == null || user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.debug(validateLogin);
            throw new ValidationException(validateLogin);
        }
        if (user.getName() == null || user.getName().equals("") ) {
            user.setName(user.getLogin());
            log.info(validateName);
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug(validateBirthday);
            throw new ValidationException(validateBirthday);
        }
    }
}
