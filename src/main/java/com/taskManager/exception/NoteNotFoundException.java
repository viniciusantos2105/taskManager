package com.taskManager.exception;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException() {
        super("Nota não encontrada");
    }
}
