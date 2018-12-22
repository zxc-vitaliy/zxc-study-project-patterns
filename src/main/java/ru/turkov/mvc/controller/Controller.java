package ru.turkov.mvc.controller;

import ru.turkov.mvc.entity.EditInfo;
import ru.turkov.mvc.model.FileModel;
import ru.turkov.mvc.model.Model;
import ru.turkov.mvc.entity.User;
import ru.turkov.mvc.model.ObservableModel;

import java.util.Collection;
import java.util.function.Consumer;

public class Controller {
    private ObservableModel model;

    public Controller(ObservableModel model) {
        this.model = model;
    }

    public void addUser(User user, Consumer<Boolean> userAdded) {
        model.addUser(user, userAdded);
    }

    public void removeUser(String id, Consumer<Boolean> userDeleted) {
        model.removeUserById(id, userDeleted);
    }

    public void updateUser(EditInfo userEditInfo, Consumer<Boolean> userChanged) {
        User user = model.getUserById(userEditInfo.getUserId());
        userEditInfo.getInfo()
                .forEach((property, value) -> {
                    switch (property) {
                        case "name":
                            user.setName(value);
                            break;
                        case "surname":
                            user.setSurname(value);
                            break;
                        case "email":
                            user.setEmail(value);
                            break;
                        case "password":
                            user.setPassword(value);
                            break;
                    }
                });
        model.editUser(user, userChanged);
    }

    public Collection<User> getUsers() {
        return model.getUsers();
    }
}
