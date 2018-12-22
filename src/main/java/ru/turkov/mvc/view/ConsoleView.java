package ru.turkov.mvc.view;

import ru.turkov.mvc.controller.Controller;
import ru.turkov.mvc.entity.EditInfo;
import ru.turkov.mvc.entity.User;

import java.util.*;
import java.util.function.Function;

public class ConsoleView implements View {
    private Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }

    public void showUsers(Collection<User> users) {
        users.forEach(System.out::println);
    }

    @Override
    public void showMenu() {
        Collection<User> users = controller.getUsers();
        while (true) {
            System.out.println("Выберите пункт: \n" +
                    "1. Вывести список пользователей \n" +
                    "2. Зарегестрировать пользователя \n" +
                    "3. Удалить пользователя \n" +
                    "4. Отредактировать пользователя \n" +
                    "5. Выйти");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();

            if (next.equals("1")) {
                users = controller.getUsers();
                showUsers(users);
            }

            if (next.equals("2")) {
                controller.addUser(registerUser(), e -> {
                    if (e) {
                        System.out.println("Успешно зарегестрировано!");
                    } else {
                        System.out.println("Ошибка на стороне модели!");
                    }
                });
            }

            if (next.equals("3")) {
                System.out.println("Введите номер пользователя, которого хотите удалить:");
                int number = Integer.parseInt(scanner.next());
                if (number < 1 || number > users.size()) {
                    System.out.println("Пользователя с таким номером не существует!");
                    continue;
                }
                ArrayList<User> usersArray = new ArrayList<>(users);

                controller.removeUser(usersArray.get(number - 1).getId(), e -> {
                    if (e) {
                        System.out.println("Успешно удалено!");
                    } else {
                        System.out.println("Ошибка на стороне модели!");
                    }
                });
            }

            if (next.equals("4")) {
                System.out.println("Введите номер пользователя, которого хотите изменить: ");
                int number = Integer.parseInt(scanner.next()) - 1;

                if (number < 0 || number > users.size()) {
                    System.out.println("Пользователя с таким номером не существует! ");
                    continue;
                }

                ArrayList<User> usersArray = new ArrayList<>(users);
                String id = usersArray.get(number).getId();

                Map<String, String> editInfo = getEditInfo();

                controller.updateUser(new EditInfo(id, editInfo), e -> {
                    if (e) {
                        System.out.println("Успешно отредактирована информация о пользователе с id = " + id);
                    } else {
                        System.out.println("Пользователя с id = " + id + " не удалось отредактировать");
                    }
                });
            }

            if (next.equals("5")) {
                break;
            }
        }
    }

    private Map<String, String> getEditInfo() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> editInfo = new HashMap<>();

        while(true) {
            System.out.println("Введите поле, которое хотите изменить(name, surname, email, password): ");
            System.out.println("Чтобы выйти введите \"-1\"");
            String property = scanner.next();

            if (property.equals("-1")) {
                break;
            }

            if (!(property.equals("name") ||
                    property.equals("surname") ||
                    property.equals("email") ||
                    property.equals("password"))) {
                System.out.println("Данного поля не существует!");
                continue;
            }

            System.out.println("Введите значение: ");
            String value = scanner.next();

            editInfo.put(property, value);
        }

        return editInfo;
    }

    private User registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id: ");
        String id = scanner.next();
        System.out.println("Введите имя: ");
        String name = scanner.next();
        System.out.println("Введите фамилию: ");
        String surname = scanner.next();
        System.out.println("Введите емаил: ");
        String email = scanner.next();
        System.out.println("Введите пароль: ");
        String password = scanner.next();
        return new User(id, name, surname, email, password);
    }
}
