package controller;

import service.TokenGenerator;

public class RequestHandlingController {

    private TokenGenerator tokenGenerator;

    public RequestHandlingController() {
        tokenGenerator = new TokenGenerator();
    }

    public String handleRequest(String request) {
        return tokenGenerator.generate();
    }

}
