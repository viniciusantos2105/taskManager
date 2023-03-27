package com.taskManager.service;

import com.taskManager.dto.NoteDTO;
import com.taskManager.exception.InvalidDateException;
import com.taskManager.model.Note;
import com.taskManager.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NoteService {

    @Autowired
    private PersonService personService;
    @Autowired
    private NoteRepository repository;

    public Note createNote(NoteDTO noteDTO){
        Date date = validateDate(noteDTO.getDate());
        Note note = new Note();
        note.setDate(date);

        return note;
    }

    public Date validateDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));

        Date now = new Date();
        String actual = formatter.format(now);

        int dayAcutal = Integer.parseInt(actual.substring(0, 2));
        int monthActual = Integer.parseInt(actual.substring(3, 5));
        int yearActual = Integer.parseInt(actual.substring(6, 10));

        if(day >= dayAcutal && month>= monthActual && year >= yearActual){
          return new Date(date);
        }
        else if(day <= dayAcutal && month > monthActual && year >= yearActual){
            return new Date(date);
        }
        else if(day <= dayAcutal && month <= monthActual && year > yearActual){
            return new Date(date);
        }
        throw new InvalidDateException();
    }
}
