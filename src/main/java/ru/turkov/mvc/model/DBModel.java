package ru.turkov.mvc.model;

import ru.turkov.mvc.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBModel implements Model<User> {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "1"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<User> getUsers() {
        try {
            ArrayList<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet usersSet = statement.executeQuery("SELECT * FROM users");
            while (usersSet.next()) {
                User user = new User();
                user.setId(usersSet.getString("id"));
                user.setName(usersSet.getString("name"));
                user.setSurname(usersSet.getString("surname"));
                user.setEmail(usersSet.getString("email"));
                user.setPassword(usersSet.getString("password"));
                users.add(user);
            }
            statement.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean addUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(String.format("INSERT INTO users VALUES('%s', '%s', '%s', '%s', '%s')",
                    user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getPassword()
            ));
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean editUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    String.format("UPDATE users SET name = '%s', surname = '%s', email = '%s', password = '%s' WHERE id = '%s'",
                            user.getName(),
                            user.getSurname(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getId()
                    )
            );
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeUserById(String userId) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(String.format("DELETE FROM users WHERE id = '%s'", userId));
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(String userId) {
        try {
            User user = new User();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM users WHERE id = '%s'", userId));
            resultSet.next();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setSurname(resultSet.getString("surname"));
            statement.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
