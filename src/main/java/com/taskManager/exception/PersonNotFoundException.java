package com.taskManager.exception;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException() {
        super("Usuario não encontrado!!");
    }
}
