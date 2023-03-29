package com.taskManager.controller;

import com.taskManager.dto.NoteDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.security.JwtTokenProvider;
import com.taskManager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @Autowired
    private JwtTokenProvider provider;

    @PostMapping("/create")
    public Note registry(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getId());
        return service.createNote(noteDTO);
    }

    @PatchMapping("/update/note/priority")
    public Note updateNotePriotiry(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getId());
        return service.updateNotePriotiry(noteDTO);
    }

    @PatchMapping("/update/note/situation")
    public Note updateNoteSituation(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getId());
        return service.updateNoteSituation(noteDTO);
    }
}
