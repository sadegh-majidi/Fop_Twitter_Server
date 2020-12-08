package service;

import exception.BadRequestException;
import model.Tweet;
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

    public void setBio(String token, String bio) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        if (!user.getBio().isEmpty())
            throw new BadRequestException("Bio is already updated.");
        user.setBio(bio);
        userRepository.updateUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + "'s bio successfully updated.");
    }

    public void changePassword(String token, String currentPassword, String newPassword) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        if (!user.getPassword().equals(currentPassword))
            throw new BadRequestException("Entered current password is wrong.");
        if (user.getPassword().equals(newPassword))
            throw new BadRequestException("New password is equal to current password.");
        user.setPassword(newPassword);
        userRepository.updateUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + "'s password successfully changed.");
    }

    public void follow(String token, String username) {
        User follower = userRepository.getAuthenticatedUserByToken(token);
        User following = userRepository.getUserByUsername(username);
        if (follower.hasFollowing(following))
            throw new BadRequestException("User " + following.getUsername() + " is already followed by you.");
        follower.addFollowing(following);
        following.addFollower(follower);
        userRepository.updateUser(follower);
        userRepository.updateUser(following);
        logger.log(LogLevel.Info, "User " + follower.getUsername() + " successfully followed user " + following.getUsername() + ".");
    }

    public void unFollow(String token, String username) {
        User follower = userRepository.getAuthenticatedUserByToken(token);
        User following = userRepository.getUserByUsername(username);
        if (!follower.hasFollowing(following))
            throw new BadRequestException("User " + following.getUsername() + " is not followed by you.");
        follower.removeFollowing(following);
        following.removeFollower(follower);
        userRepository.updateUser(follower);
        userRepository.updateUser(following);
        logger.log(LogLevel.Info, "User " + follower.getUsername() + " successfully unFollowed user " + following.getUsername() + ".");
    }

    //TODO: har nafar faghat mitone 1 bar like kone ya mohem nis??
    public void likeTweet(String token, int tweetId) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Tweet tweet = tweetsRepository.getTweetById(tweetId);
        tweet.increaseLikes();
        tweetsRepository.updateTweet(tweet);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully liked tweet " + tweet.getId() + ".");
    }

    public void sendTweet(String token, String content) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Tweet tweet = new Tweet(content);
        tweetsRepository.addTweet(tweet);
        user.addTweet(tweet);
        userRepository.updateUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully send a tweet.");
    }
}
