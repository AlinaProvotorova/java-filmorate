package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
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

    public static void validateNotFoundId(Set<Integer> listObj, Integer id) {
        if (id < 0) {
            throw new NotFoundException("Id может быть только положительным");
        }
        if (!(listObj.contains(id))) {
            throw new NotFoundException(String.format("Пользователя с id %s не существует", id));
        }
    }
}
