package com.taskManager.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
        super("Senha incorreta!!! Tente Novamente!  ");
    }
}
