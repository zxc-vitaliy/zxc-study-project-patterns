//package ru.turkov.mvc.presenter;
//
//import ru.turkov.mvc.entity.EditInfo;
//import ru.turkov.mvc.model.Model;
//import ru.turkov.mvc.entity.User;
//import ru.turkov.mvc.view.View;
//
//import java.util.function.Function;
//
//public class Presenter {
//    private View view;
//    private Model<User> model;
//
//    public Presenter(View view, Model<User> model) {
//        this.model = model;
//        this.view = view;
//    }
//
//    public void execute() {
//        view.showMenu(
//                user -> model.addUser(user),
//                userNumber -> model.removeUserById(userNumber),
//                getEditInfoConsumer(),
//                () -> model.getUsers());
//    }
//
//    private Function<EditInfo, Boolean> getEditInfoConsumer() {
//        return userInfo -> {
//            User user = model.getUserById(userInfo.getUserId());
//            userInfo.getInfo()
//                    .forEach((property, value) -> {
//                        switch (property) {
//                            case "name":
//                                user.setName(value);
//                                break;
//                            case "surname":
//                                user.setSurname(value);
//                                break;
//                            case "email":
//                                user.setEmail(value);
//                                break;
//                            case "password":
//                                user.setPassword(value);
//                                break;
//                        }
//                    });
//            return model.editUser(user);
//        };
//    }
//}
