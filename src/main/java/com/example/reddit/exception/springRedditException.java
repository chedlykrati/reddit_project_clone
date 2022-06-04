package com.example.reddit.exception;

import org.springframework.mail.MailException;

public class springRedditException extends RuntimeException {
    public springRedditException(String exMessage, Exception exception) {

        super(exMessage , exception);
    }




}
