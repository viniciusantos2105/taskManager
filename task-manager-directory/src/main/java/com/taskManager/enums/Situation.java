package com.taskManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Situation {

    OPENED(1, "em aberto"),
    DONE(2, "feito");

    private final int number;
    private final String description;
}
