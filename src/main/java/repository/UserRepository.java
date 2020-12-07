package repository;

import exception.BadRequestException;
import model.User;
import utils.JsonFileWriterUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    public Map<String, User> allUsers;
    public Map<String, User> authenticatedUsersByToken;
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    private UserRepository() {
        allUsers = new HashMap<>();
        authenticatedUsersByToken = new HashMap<>();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }

    public Map<String, User> getAllAuthenticatedUsers() {
        return  authenticatedUsersByToken;
    }

    public User getUserByUsername(String username) {
        return allUsers.get(username);
    }

    public User getAuthenticatedUserByToken(String token) {
        return authenticatedUsersByToken.get(token);
    }

    public void addUser(User user) {
        if (this.allUsers.containsKey(user.getUsername()))
            throw new BadRequestException("This username is already taken.");
        this.allUsers.put(user.getUsername(), user);
        JsonFileWriterUtil fileWriter = new JsonFileWriterUtil();
        String filePath = "Resources/Users/" + user.getUsername() + ".user.json";
        try {
            fileWriter.write(user, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOnlineUser(User user) {
        this.authenticatedUsersByToken.put(user.getToken(), user);
    }

    public void updateUser(String username, User user) {
        this.allUsers.replace(username, user);
        JsonFileWriterUtil fileWriter = new JsonFileWriterUtil();
        String filePath = "Resources/Users/" + username + ".user.json";
        try {
            fileWriter.write(user, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        this.allUsers.remove(username);
        String filePath = "Resources/Users/" + username + ".user.json";
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }

    public void deleteOnlineUser(String token) {
        this.authenticatedUsersByToken.remove(token);
    }
}
