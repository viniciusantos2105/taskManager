package com.taskManager.exception;

public class TokenInvalidException extends RuntimeException{

    public TokenInvalidException() {
        super("Operação inválida!!!");
    }
}
