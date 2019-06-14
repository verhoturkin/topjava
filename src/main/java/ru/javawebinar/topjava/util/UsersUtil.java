package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN),
            new User("Vasys", "pupkin@ya.ru", "pupkin", Role.ROLE_USER),
            new User("Irina", "666@hotmail.com", "aveSatan!", Role.ROLE_USER)
    );
}
