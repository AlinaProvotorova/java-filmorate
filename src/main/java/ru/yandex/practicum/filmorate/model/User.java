package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Builder
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String email;
    private String name;
    private LocalDate birthday;
    private final Set<Integer> idFriends = new TreeSet<>();

    public User addFriend(User user) {
        this.idFriends.add(user.getId());
        user.idFriends.add(this.getId());
        return this;
    }

    public void deleteFriend(User user) {
        this.idFriends.remove(user.getId());
        user.idFriends.remove(this.getId());
    }

    public Set<Integer> getIdFriends() {
        return new HashSet<>(this.idFriends);
    }
}
