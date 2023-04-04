package com.taskManager.controller;

import com.taskManager.dto.NoteDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.security.JwtTokenProvider;
import com.taskManager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @Autowired
    private JwtTokenProvider provider;

    @PostMapping("/create")
    public Note registry(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.createNote(noteDTO);
    }

    @PatchMapping("/update/priority")
    public Note updateNotePriotiry(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.updateNotePriotiry(noteDTO);
    }

    @PatchMapping("/update/situation")
    public Note updateNoteSituation(@RequestBody NoteDTO noteDTO, @RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), noteDTO.getPersonId());
        return service.updateNoteSituation(noteDTO);
    }

    @GetMapping("/list/{id}")
    public List<Note> findAll(@PathVariable Long id,@RequestHeader("Authorization") String token){
        provider.validate(token.substring(7), id);
        return service.findAll(id);
    }
}
