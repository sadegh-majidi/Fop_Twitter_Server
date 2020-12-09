package model;

import com.google.gson.annotations.Expose;
import repository.TweetsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class Profile {
    private String username;

    private String bio;

    private int numberOfFollowers;

    private int numberOfFollowings;

    private FollowStatus followStatus;

    private List<Tweet> allTweets;

    public Profile(User user, FollowStatus status) {
        this.username = user.getUsername();
        this.bio = user.getBio();
        this.numberOfFollowers = user.getFollowers().size();
        this.numberOfFollowings = user.getFollowings().size();
        this.followStatus = status;
        TweetsRepository tweetsRepository = TweetsRepository.getInstance();
        this.allTweets = user.getPersonalTweets().stream().map(tweetsRepository::getTweetById).collect(Collectors.toList());
    }

}
