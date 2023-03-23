package com.taskManager.dto;

import com.taskManager.enums.Priority;
import com.taskManager.enums.Situation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {


    private String title;
    private String description;
    private String date;
    private String priority;

}
