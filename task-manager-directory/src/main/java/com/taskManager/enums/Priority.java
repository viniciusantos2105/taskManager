package com.taskManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {

    MAXIMUM(1, "maxima"),
    MEAN(2, "media"),
    LOW(3, "baixa");

    private final int number;
    private final String description;
}
