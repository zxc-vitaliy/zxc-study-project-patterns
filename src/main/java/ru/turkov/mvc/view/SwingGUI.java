package ru.turkov.mvc.view;

import ru.turkov.mvc.controller.Controller;
import ru.turkov.mvc.entity.EditInfo;
import ru.turkov.mvc.entity.User;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.swing.*;

public class SwingGUI extends JFrame implements View {

    private Controller controller;

    public SwingGUI(Controller controller) {
        super("Пользователи");
        this.controller = controller;
        setBounds(100, 100, 500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton showUsersButton = new JButton("Показать всех пользователей");
        JButton deleteUserButton = new JButton("Удалить пользователя");
        JButton editUserButton = new JButton("Изменить данные о пользователе");
        JButton addUserButton = new JButton("Добавить пользователя");

        GridLayout gridLayout = new GridLayout(4, 1);
        showUsersButton.addActionListener(e -> showUsersAction());
        addUserButton.addActionListener(e -> addUserAction());
        deleteUserButton.addActionListener(e -> deleteUserAction());
        editUserButton.addActionListener(e -> editUsersAction());
        setLayout(gridLayout);
        add(showUsersButton);
        add(addUserButton);
        add(deleteUserButton);
        add(editUserButton);
        setVisible(true);
    }

    @Override
    public void showMenu() {
        setVisible(true);
    }

    private void editUsersAction() {
        String[] columnNames = {
                "Name",
                "Surname",
                "Email",
                "Password"
        };

        List<String[]> users = controller.getUsers()
                .stream()
                .map(user -> new String[]{user.getName(), user.getSurname(), user.getEmail(), user.getPassword()})
                .collect(Collectors.toList());

        String[][] data = new String[users.size()][];
        int i = 0;
        for (String[] user : users) {
            data[i] = user;
            i++;
        }

        JFrame frame = new JFrame();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(650, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JButton saveButton = new JButton("Сохранить");

        saveButton.addActionListener(e -> {
            saveEditedUsers(table, frame);
        });
        frame.add(saveButton);
    }

    private void saveEditedUsers(JTable table, Frame frame) {
        ArrayList<User> users = new ArrayList<>(controller.getUsers());
        for (int i = 0; i < users.size(); i++) {
            Map<String, String> editInfoMap = new HashMap<>();
            editInfoMap.put("name", (String) table.getValueAt(i, 0));
            editInfoMap.put("surname", (String) table.getValueAt(i, 1));
            editInfoMap.put("email", (String) table.getValueAt(i, 2));
            editInfoMap.put("password", (String) table.getValueAt(i, 3));
            String id = users.get(i).getId();
            controller.updateUser(new EditInfo(id, editInfoMap), e -> {
                if (e) {
                    JFrame ok = new JFrame("Сообщение");
                    ok.setBounds(100, 100, 350, 100);
                    ok.add(new Label("Изменения успешно сохранены"));
                    ok.setVisible(true);
                    frame.setVisible(false);
                } else {
                    JFrame error = new JFrame("Сообщение");
                    error.setBounds(100, 100, 350, 100);
                    error.add(new Label("Не оставляйте пустых ячеек"));
                    error.setVisible(true);
                }
            });
        }
    }

    private void deleteUserAction() {
        JLabel userNumberLabel = new JLabel("Номер пользователя в таблице:");
        JTextField userNumberField = new JTextField();
        JFrame deleteUserFrame = new JFrame("Удаление пользователя");
        deleteUserFrame.setLayout(new GridLayout(2, 2));
        deleteUserFrame.add(userNumberLabel);
        deleteUserFrame.add(userNumberField);
        deleteUserFrame.setBounds(100, 100, 400, 100);

        JButton deleteUserButton = new JButton("Удалить");
        deleteUserButton.addActionListener(e -> {
            try {
                int userNumber = Integer.parseInt(userNumberField.getText());
                if (userNumber > controller.getUsers().size() || userNumber < 1) {
                    JFrame error = new JFrame("Ошибка");
                    error.setBounds(100, 100, 300, 100);
                    error.add(new Label("Такого пользователя не существует"));
                    error.setVisible(true);
                    return;
                }
                ArrayList<User> users = new ArrayList<>(controller.getUsers());
                controller.removeUser(users.get(userNumber - 1).getId(), result -> {
                    if (result) {
                        JFrame ok = new JFrame("Успешно");
                        ok.setBounds(100, 100, 300, 100);
                        ok.add(new Label("Удаление прошло успешно"));
                        ok.setVisible(true);
                    } else {
                        JFrame error = new JFrame("Ошибка");
                        error.setBounds(100, 100, 300, 100);
                        error.add(new Label("Не удалось удалить"));
                        error.setVisible(true);
                    }
                });
            } catch (NumberFormatException exception) {
                JFrame error = new JFrame("Ошибка");
                error.setBounds(100, 100, 300, 100);
                error.add(new Label("Введите корректный номер пользователя"));
                error.setVisible(true);
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> {
            deleteUserFrame.setVisible(false);
        });

        deleteUserFrame.add(deleteUserButton);
        deleteUserFrame.add(cancelButton);
        deleteUserFrame.setVisible(true);
    }

    private void addUserAction() {
        JTextField idField = new JTextField();
        JLabel idLabel = new JLabel("id");
        JTextField nameField = new JTextField();
        JLabel nameLabel = new JLabel("Имя");
        JTextField surnameField = new JTextField();
        JLabel surnameLabel = new JLabel("Фамилия");
        JTextField emailField = new JTextField();
        JLabel emailLabel = new JLabel("Емаил");
        JTextField passwordField = new JTextField();
        JLabel passwordLabel = new JLabel("Пароль");
        JButton addUserButton = new JButton("Добавить");
        JButton cancelButton = new JButton("Отмена");

        JFrame addUserFrame = new JFrame("Добавление пользователя");

        GridLayout addUserLayout = new GridLayout(6, 2);
        addUserFrame.setLayout(addUserLayout);
        addUserFrame.add(idLabel);
        addUserFrame.add(idField);
        addUserFrame.add(nameLabel);
        addUserFrame.add(nameField);
        addUserFrame.add(surnameLabel);
        addUserFrame.add(surnameField);
        addUserFrame.add(emailLabel);
        addUserFrame.add(emailField);
        addUserFrame.add(passwordLabel);
        addUserFrame.add(passwordField);
        addUserFrame.add(addUserButton);
        addUserFrame.add(cancelButton);

        addUserButton.addActionListener(e -> {
            if (idField.getText().isEmpty() ||
                    nameField.getText().isEmpty() ||
                    surnameField.getText().isEmpty() ||
                    emailField.getText().isEmpty() ||
                    passwordField.getText().isEmpty()) {
                JFrame error = new JFrame("Ошибка");
                error.setBounds(100, 100, 250, 100);
                error.add(new Label("Все поля должны быть заполнены"));
                error.setVisible(true);
                return;
            }
            controller.addUser(new User(
                    idField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    emailField.getText(),
                    passwordField.getText()), event -> {
                if (event) {
                    JFrame ok = new JFrame("Сообщение");
                    ok.setBounds(100, 100, 250, 100);
                    ok.add(new Label("Пользователь успешно сохранен"));
                    ok.setVisible(true);
                    addUserFrame.setVisible(false);
                } else {
                    JFrame error = new JFrame("Ошибка");
                    error.setBounds(100, 100, 300, 100);
                    error.add(new Label("Не удалось добавить"));
                    error.setVisible(true);
                }
            });
        });

        cancelButton.addActionListener(e -> addUserFrame.setVisible(false));
        addUserFrame.setBounds(100, 100, 250, 200);
        addUserFrame.setVisible(true);
    }

    private void showUsersAction() {
        String[] columnNames = {
                "Name",
                "Surname",
                "Email",
                "Password"
        };

        List<String[]> users = controller.getUsers()
                .stream()
                .map(user -> new String[]{user.getName(), user.getSurname(), user.getEmail(), user.getPassword()})
                .collect(Collectors.toList());

        String[][] data = new String[users.size()][];
        int i = 0;
        for (String[] user : users) {
            data[i] = user;
            i++;
        }

        JFrame frame = new JFrame();

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        GridLayout layout = new GridLayout(1, 1);
        frame.setLayout(layout);

        frame.add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}