package ru.turkov.mvc.model;

import ru.turkov.mvc.entity.User;

import java.util.Collection;
import java.util.function.Consumer;

public class ObservableModel {

    private Model<User> model;

    public ObservableModel(Model model) {
        this.model = model;
    }

    public void addUser(User user, Consumer<Boolean> userAdded) {
        if (model.addUser(user)) {
            userAdded.accept(true);
        } else {
            userAdded.accept(false);
        }
    }

    public void removeUserById(String id, Consumer<Boolean> userDeleted) {
        if (model.removeUserById(id)) {
            userDeleted.accept(true);
        } else {
            userDeleted.accept(false);
        }
    }

    public void editUser(User user, Consumer<Boolean> userChanged) {
        if (model.editUser(user)) {
            userChanged.accept(true);
        } else {
            userChanged.accept(false);
        }
    }

    public Collection<User> getUsers() {
        return model.getUsers();
    }

    public User getUserById(String userId) {
        return model.getUserById(userId);
    }
}
