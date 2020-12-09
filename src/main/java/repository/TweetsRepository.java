package repository;

import exception.BadRequestException;
import model.Tweet;
import utils.JsonFileWriterUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetsRepository {
    public Map<Integer, Tweet> allTweets;
    private static TweetsRepository instance;

    public static TweetsRepository getInstance() {
        if (instance == null)
            instance = new TweetsRepository();
        return instance;
    }

    private TweetsRepository() {
        allTweets = new HashMap<>();
    }

    public List<Tweet> getAllTweets() {
        return new ArrayList<>(allTweets.values());
    }

    public Tweet getTweetById(int id) {
        if (!allTweets.containsKey(id))
            throw new BadRequestException("Tweet with this id is not available.");
        return allTweets.get(id);
    }

    public void addTweet(Tweet tweet) {
        if (allTweets.containsKey(tweet.getId()))
            throw new BadRequestException("Can not add tweet because there is a tweet with this id.");
        this.allTweets.put(tweet.getId(), tweet);
        JsonFileWriterUtil fileWriter = new JsonFileWriterUtil();
        String filePath = "Resources/Tweets/" + tweet.getId() + ".tweet.json";
        try {
            fileWriter.write(tweet, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTweet(Tweet tweet) {
        JsonFileWriterUtil fileWriter = new JsonFileWriterUtil();
        String filePath = "Resources/Tweets/" + tweet.getId() + ".tweet.json";
        try {
            fileWriter.write(tweet, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTweet(int id) {
        this.allTweets.remove(id);
        String filePath = "Resources/Tweets/" + id + ".tweet.json";
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }
}
