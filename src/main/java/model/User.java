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

    public void addFollower(String user) {
        this.followers.add(user);
    }

    //TODO: add Exception user not found
    public void removeFollower(String user) {
        this.followers.remove(user);
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public void addFollowing(String user) {
        this.followings.add(user);
    }

    //TODO: add Exception user not found
    public void removeFollowing(String user) {
        this.followings.remove(user);
    }

    //TODO: override
    public List<Integer> getPersonalTweets() {
        return personalTweets;
    }

    public void addTweet(int tweetId) {
        this.personalTweets.add(tweetId);
    }
}
