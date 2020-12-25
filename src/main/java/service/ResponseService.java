package service;

import exception.BadRequestException;
import model.*;
import repository.TweetsRepository;
import repository.UserRepository;
import utils.LogLevel;
import utils.Logger;
import utils.TokenGenerator;

import java.util.*;
import java.util.stream.Collectors;

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
        if (follower.getUsername().equals(following.getUsername()))
            throw new BadRequestException("You can't follow yourself!");
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
        if (follower.getUsername().equals(following.getUsername()))
            throw new BadRequestException("You can not unFollow yourself!");
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
        Tweet tweet = new Tweet(user.getUsername(), content);
        tweetsRepository.addTweet(tweet);
        user.addTweet(tweet);
        userRepository.updateUser(user);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully send a tweet.");
    }

    public void commentTweet(String token, int tweetId, String content) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Tweet tweet = tweetsRepository.getTweetById(tweetId);
        tweet.addComment(user.getUsername(), content);
        tweetsRepository.updateTweet(tweet);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully send a comment.");
    }

    public List<Tweet> refreshTimeLine(String token) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        List<Tweet> allTimeLineTweets = new ArrayList<>();
        List<String> allConnectedUsers = new ArrayList<>(mergeSets(user.getFollowers(), user.getFollowings()));
        Collections.sort(allConnectedUsers);
        for (String connectedUser : allConnectedUsers) {
            User _user = userRepository.getUserByUsername(connectedUser);
            allTimeLineTweets.addAll(_user.getPersonalTweets().stream()
                    .map(id -> tweetsRepository.getTweetById(id)).collect(Collectors.toList()));
        }
        List<Tweet> result = user.getTimeLineIndex() < allTimeLineTweets.size() ?
                allTimeLineTweets.subList(user.getTimeLineIndex(), allTimeLineTweets.size()) : Collections.emptyList();
        user.setTimeLineIndex(allTimeLineTweets.size());
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully refreshed timeline.");
        return result;
    }

    public Profile search(String token, String username) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        User searchedUser = userRepository.getUserByUsername(username);
        Profile profile;
        if (user.hasFollowing(searchedUser))
            profile = new Profile(searchedUser, FollowStatus.Followed);
        else
            profile = new Profile(searchedUser, FollowStatus.NotFollowed);
        logger.log(LogLevel.Info, "User " + user + " successfully searched user " + searchedUser.getUsername() + ".");
        return profile;
    }

    public Profile getProfile(String token) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Profile profile = new Profile(user, FollowStatus.Yourself);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully viewed his/her profile.");
        return profile;
    }

    public Map<String, String> getComments(String token, int tweetId) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Tweet tweet = tweetsRepository.getTweetById(tweetId);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully received comments of tweet " + tweetId + ".");
        return tweet.getComments();
    }

    public int getLikes(String token, int tweetId) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        Tweet tweet = tweetsRepository.getTweetById(tweetId);
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully received likes of tweet " + tweetId + ".");
        return tweet.getLikes();
    }

    public List<TweetView> refreshTimeLineTweetView(String token) {
        User user = userRepository.getAuthenticatedUserByToken(token);
        List<Tweet> allTimeLineTweets = new ArrayList<>();
        List<String> allConnectedUsers = new ArrayList<>(mergeSets(user.getFollowers(), user.getFollowings()));
        Collections.sort(allConnectedUsers);
        for (String connectedUser : allConnectedUsers) {
            User _user = userRepository.getUserByUsername(connectedUser);
            allTimeLineTweets.addAll(_user.getPersonalTweets().stream()
                    .map(id -> tweetsRepository.getTweetById(id)).collect(Collectors.toList()));
        }
        List<TweetView> result = user.getTimeLineIndex() < allTimeLineTweets.size() ?
                allTimeLineTweets.subList(user.getTimeLineIndex(), allTimeLineTweets.size())
                        .stream().map(TweetView::new).collect(Collectors.toList()) : Collections.emptyList();
        user.setTimeLineIndex(allTimeLineTweets.size());
        logger.log(LogLevel.Info, "User " + user.getUsername() + " successfully refreshed timeline.");
        return result;
    }

    private Set<String> mergeSets(Set<String> a, Set<String> b) {
        return new HashSet<String>() {{
            addAll(a);
            addAll(b);
        }};
    }

}
