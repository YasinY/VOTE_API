package com.yasin.voteapi.status;

import org.springframework.http.ResponseEntity;

/**
 * @author Yasin
 */
public enum Status {
    ERROR_USERNAME_INVALID(901, "Username given is invalid."),
    ERROR_NO_USER_FOUND(902, "Username not found"),
    INFO_USER_ALREADY_VOTED(801, "Username already voted!"),
    INFO_LAST_VOTE_DELAY(802, "User has to wait until next reward");

    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseEntity<String> build() {
        return ResponseEntity.status(code).build();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
