package controller;

import java.util.regex.Pattern;

public enum RequestPatternType {
    SignUp(Pattern.compile("^signup\\s+(\\S+)\\s*,\\s*(\\S+)$")),
    Login(Pattern.compile("^login\\s+(\\S+)\\s*,\\s*(\\S+)$")),
    SendTweet(Pattern.compile("^send\\s+tweet\\s+(.+)\\s*,\\s*(.+)$")),
    RefreshTimeLine(Pattern.compile("^refresh\\s+(.+)$")),
    LikeTweet(Pattern.compile("^like\\s+(.+)\\s*,\\s*(\\d+)$")),
    CommentTweet(Pattern.compile("^comment\\s+(.+)\\s*,\\s*(\\d+)\\s*,\\s*(.+)$")),
    SearchUser(Pattern.compile("^search\\s+(.+)\\s*,\\s*(\\S+)$")),
    Follow(Pattern.compile("^follow\\s+(.+)\\s*,\\s*(\\S+)$")),
    UnFollow(Pattern.compile("^unfollow\\s+(.+)\\s*,\\s*(\\S+)$")),
    Profile(Pattern.compile("^profile\\s+(.+)$")),
    SetBio(Pattern.compile("^set\\s+bio\\s+(.+)\\s*,\\s*(.+)$")),
    ChangePassword(Pattern.compile("^change\\s+password\\s+(.+)\\s*,\\s*(\\S+)\\s*,\\s*(\\S+)$")),
    Logout(Pattern.compile("^logout\\s+(.+)$"));
    private final Pattern pattern;

    RequestPatternType(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
