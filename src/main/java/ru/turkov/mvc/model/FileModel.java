package ru.turkov.mvc.model;

import ru.turkov.mvc.entity.User;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileModel implements Model<User> {
    @Override
    public Collection<User> getUsers() {
        try {
            final Path inputFile = Paths.get(this.getClass().getResource("/users.txt").toURI());
            try (final Stream<String> lines = Files.lines(inputFile)) {
                return lines
                        .filter(line -> line != null && !line.trim().equals(""))
                        .map(line -> {
                            String[] s = line.split(" ");
                            return new User(s[0], s[1], s[2], s[3], s[4]);
                        })
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean addUser(User user) {
        try {
            FileWriter fileWriter = new FileWriter(this.getClass().getResource("/users.txt").getPath(), true);
            fileWriter.append(user.toString()).append("\n");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean editUser(User user) {
        try {
            final Path inputFile = Paths.get(ClassLoader.getSystemResource("users.txt").toURI());
            final Stream<String> lines = Files.lines(inputFile);
            List<String> users = lines
                    .map(line -> {
                        if (line.split(" ")[0].equals(user.getId())) {
                            return user.toString();
                        } else {
                            return line;
                        }
                    })
                    .collect(Collectors.toList());

            Writer fileWriter = new FileWriter(new File(this.getClass().getResource("/users.txt").getPath()));
            users.forEach(userData -> {
                try {
                    fileWriter.append(userData).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
            return true;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeUserById(String userId) {
        try {
            List<User> users = getUsers()
                    .stream()
                    .filter(user -> !user.getId().equals(userId))
                    .collect(Collectors.toList());

            Writer fileWriter = new FileWriter(new File(this.getClass().getResource("/users.txt").getPath()));
            users.forEach(this::addUser);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(String userId) {
        ArrayList<User> users = new ArrayList<>(getUsers());
        List<User> searchedUser = users.stream()
                .filter(user -> user.getId().equals(userId))
                .collect(Collectors.toList());
        return searchedUser.isEmpty() ? null : searchedUser.get(0);
    }
}
