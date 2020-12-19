package model;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class Tweet {
    private static int numberOfTweets = 0;

    @Expose
    private int id;

    @Expose
    private String author;

    @Expose
    private String content;

    @Expose
    private Map<String, String> comments;

    @Expose
    private int likes;

    public Tweet(String author, String content) {
        this.id = ++numberOfTweets;
        this.author = author;
        this.content = content;
        this.comments = new HashMap<>();
        this.likes = 0;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static int getNumberOfTweets() {
        return numberOfTweets;
    }

    public static void setNumberOfTweets(int numberOfTweets) {
        Tweet.numberOfTweets = numberOfTweets;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public void addComment(String commenter, String content) {
        this.comments.put(commenter, content);
    }

    //TODO: add conditions and exception
    public void removeComment(String commenter) {
        this.comments.remove(commenter);
    }

    public int getLikes() {
        return likes;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }
}
