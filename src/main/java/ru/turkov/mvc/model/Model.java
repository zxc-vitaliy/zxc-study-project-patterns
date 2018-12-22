package ru.turkov.mvc.model;

import java.util.Collection;
import java.util.function.Function;

public interface Model<User> {
    Collection<User> getUsers();
    Boolean addUser(User user);
    Boolean editUser(User user);
    Boolean removeUserById(String userId);
    User getUserById(String userId);
}
