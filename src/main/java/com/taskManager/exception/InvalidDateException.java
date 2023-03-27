package com.taskManager.exception;

public class InvalidDateException extends RuntimeException{

    public InvalidDateException() {
        super("Data inválida!!");
    }
}
