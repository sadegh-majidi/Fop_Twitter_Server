package model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    @Expose
    private String username;

    @Expose
    private String password;

    private String token;

    private int timeLineIndex;

    @Expose
    private String bio;

    @Expose
    private Set<String> followers;

    @Expose
    private Set<String> followings;

    @Expose
    private List<Integer> personalTweets;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bio = "";
        this.timeLineIndex = 0;
        this.token = null;
        this.followers = new HashSet<>();
        this.followings = new HashSet<>();
        this.personalTweets = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void addFollower(User user) {
        this.followers.add(user.getUsername());
    }

    public void removeFollower(User user) {
        this.followers.remove(user.getUsername());
    }

    public boolean hasFollower(User user) {
        return this.followers.contains(user.getUsername());
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public void addFollowing(User user) {
        this.followings.add(user.getUsername());
    }

    public void removeFollowing(User user) {
        this.followings.remove(user.getUsername());
    }

    public boolean hasFollowing(User user) {
        return this.followings.contains(user.getUsername());
    }

    public List<Integer> getPersonalTweets() {
        return personalTweets;
    }

    public void addTweet(Tweet tweet) {
        this.personalTweets.add(tweet.getId());
    }

    public int getTimeLineIndex() {
        return timeLineIndex;
    }

    public void setTimeLineIndex(int timeLineIndex) {
        this.timeLineIndex = timeLineIndex;
    }

}
