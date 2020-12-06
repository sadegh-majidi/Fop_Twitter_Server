package server;

import model.Tweet;
import model.User;
import repository.TweetsRepository;
import repository.UserRepository;
import utils.JsonFileReaderUtil;

import java.io.File;
import java.io.FileNotFoundException;

public class ServerEventHandler {
    private UserRepository userRepository;
    private TweetsRepository tweetsRepository;

    public ServerEventHandler() {
        userRepository = UserRepository.getInstance();
        tweetsRepository = TweetsRepository.getInstance();
    }

    public void init() {
        JsonFileReaderUtil fileReader = new JsonFileReaderUtil();
        initUsers(fileReader);
        initTweets(fileReader);
    }

    private void initUsers(JsonFileReaderUtil reader) {
        File usersDirectory = new File("Resources/Users/");
        if (!usersDirectory.exists())
            usersDirectory.mkdirs();
        File[] userFiles = usersDirectory.listFiles();
        if (userFiles != null) {
            for (File file : userFiles) {
                try {
                    User user = reader.read(file, User.class);
                    userRepository.allUsers.put(user.getUsername(), user);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initTweets(JsonFileReaderUtil reader) {
        File tweetsDirectory = new File("Resources/Tweets/");
        if (!tweetsDirectory.exists())
            tweetsDirectory.mkdirs();
        File[] tweetsFiles = tweetsDirectory.listFiles();
        if (tweetsFiles != null) {
            for (File file : tweetsFiles) {
                try {
                    Tweet tweet = reader.read(file, Tweet.class);
                    tweetsRepository.allTweets.put(tweet.getId(), tweet);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
