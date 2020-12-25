package model;

public class TweetView {
    private int id;
    private String author;
    private String content;

    public TweetView(Tweet tweet) {
        this.id = tweet.getId();
        this.author = tweet.getAuthor();
        this.content = tweet.getContent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
