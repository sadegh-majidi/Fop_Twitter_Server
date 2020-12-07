package service;

import exception.BadRequestException;
import model.User;
import repository.TweetsRepository;
import repository.UserRepository;
import utils.LogLevel;
import utils.Logger;

public class ResponseService {
    private final Logger logger;
    private TokenGenerator tokenGenerator;
    private TweetsRepository tweetsRepository;
    private UserRepository userRepository;

    public ResponseService(Logger logger) {
        this.logger = logger;
        tokenGenerator = new TokenGenerator();
        tweetsRepository = TweetsRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public void signUp(String username, String password) {
        User user = new User(username, password);
        userRepository.addUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully registered.");
    }

    public String login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (!user.getPassword().equals(password))
            throw new BadRequestException("Incorrect password.");
        if (user.getToken() != null)
            throw new BadRequestException("The user " + user.getUsername() + " is already logged in.");
        user.setToken(tokenGenerator.generate());
        userRepository.addOnlineUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully logged in.");
        return user.getToken();
    }

    public void logout(String token) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        userRepository.deleteOnlineUser(token);
        user.setToken(null);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully logged out.");
    }

}
