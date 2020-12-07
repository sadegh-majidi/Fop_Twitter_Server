package service;

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
        return "";
    }

    public void logout(String token) {

    }
}
