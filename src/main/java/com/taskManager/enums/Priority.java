package com.taskManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {

    MAXIMUM(1, "Máximo"),
    MEAN(2, "Média"),
    LOW(3, "Baixa");

    private final int number;
    private final String description;
}
