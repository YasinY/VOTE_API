package com.yasin.voteapi.http.response;

/**
 * @author Yasin
 */
public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
