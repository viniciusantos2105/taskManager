package com.taskManager.controller;

import com.taskManager.dto.NoteDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @PostMapping("/create")
    public Note registry(@RequestBody NoteDTO noteDTO){
        return service.createNote(noteDTO);
    }
}
