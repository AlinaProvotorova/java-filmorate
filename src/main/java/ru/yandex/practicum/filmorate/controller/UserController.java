package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Integer id) {
        return service.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getFriendsOfFriend(@PathVariable Integer id, @PathVariable Integer otherId) {
        return service.getFriendsOfFriend(id, otherId);
    }

    @GetMapping("/{id}/friends/incoming")
    public List<User> getIncomingFriendsRequests(@PathVariable Integer id) {
        return service.getIncomingFriendsRequests(id);
    }

    @GetMapping("/{id}/friends/outgoing")
    public List<User> getOutgoingFriendsRequests(@PathVariable Integer id) {
        return service.getOutgoingFriendsRequests(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String sendFriendshipRequest(@PathVariable Integer id, @PathVariable Integer friendId) {
        service.sendFriendshipRequest(id, friendId);
        return "Заявка другу " + friendId + " успешно отправленна";
    }

    @PutMapping("/{id}/friends/accept/{friendId}")
    public String acceptFriendship(@PathVariable Integer id, @PathVariable Integer friendId) {
        service.acceptFriendship(id, friendId);
        return "Заявка от друга " + friendId + " принята";
    }

    @DeleteMapping("/{id}/friends/reject/{friendId}")
    public String rejectFriendship(@PathVariable Integer id, @PathVariable Integer friendId) {
        service.rejectFriendship(id, friendId);
        return "Заявка от друга " + friendId + " отклонена";
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteToFrend(@PathVariable Integer id, @PathVariable Integer friendId) {
        service.deleteFriend(id, friendId);
        return "Друг " + friendId + "  удален";
    }

    @GetMapping("/{id}/recommendations")
    public List<Film> getRecommendations(@PathVariable Integer id) {
        return service.recommendations(id);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "Пользователь " + id + " удален";
    }
}
