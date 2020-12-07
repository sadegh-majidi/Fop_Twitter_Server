package controller;

import exception.BadRequestException;
import model.Response;
import model.ResponseType;
import service.TokenGenerator;

import java.util.regex.Matcher;

import static controller.RequestPatternType.*;

public class RequestHandlingController {

    private TokenGenerator tokenGenerator;

    public RequestHandlingController() {
        tokenGenerator = new TokenGenerator();
    }

    public String handleRequest(String request) {
        try {
            Matcher matcher;
            if ((matcher = SignUp.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = Login.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = SendTweet.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = RefreshTimeLine.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = LikeTweet.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = CommentTweet.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = SearchUser.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = Follow.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = UnFollow.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = Profile.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = SetBio.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = ChangePassword.getPattern().matcher(request)).find())
                return "";
            else if ((matcher = Logout.getPattern().matcher(request)).find())
                return "";
            else
                throw new BadRequestException("Bad request format.");
        } catch (BadRequestException e) {
            return new Response<>(ResponseType.Error, e.getMessage()).convertToJson();
        }
    }

}
