package com.taskManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Situation {

    OPENED(1, "Em aberto"),
    DONE(2, "Feito");

    private final int number;
    private final String description;
}
