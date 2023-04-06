package com.taskManager.exception;

public class UsernameInUseException extends RuntimeException{

    public UsernameInUseException() {
        super("Username indisponivel!");
    }
}
