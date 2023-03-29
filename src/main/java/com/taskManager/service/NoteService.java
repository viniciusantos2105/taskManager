package com.taskManager.service;

import com.taskManager.dto.NoteDTO;
import com.taskManager.enums.Priority;
import com.taskManager.enums.Situation;
import com.taskManager.exception.InvalidDateException;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
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
        Person person = personService.findById(noteDTO.getId());

        String date = validateDate(noteDTO.getDate());
        Note note = new Note();

        note.setDate(date);
        note.setTitle(noteDTO.getTitle());
        note.setDescription(noteDTO.getDescription());
        note.setSituation(Situation.OPENED);
        note.setPriority(selectPriority(noteDTO.getPriority()));

        person.getNoteList().add(note);
        return repository.save(note);
    }

    public Note updateNotePriotiry(NoteDTO noteDTO){
        Note note = repository.findById(noteDTO.getId()).orElseThrow();

        note.setPriority(selectPriority(noteDTO.getPriority()));
        return repository.save(note);
    }

    public Note updateNoteSituation(NoteDTO noteDTO){
        Note note = repository.findById(noteDTO.getId()).orElseThrow();

        if(noteDTO.getSituation().equals("feito")){
            note.setSituation(Situation.DONE);
            return repository.save(note);
        }
        return note;
    }

    public Priority selectPriority(String priorityString){
        Priority priority = null;
        if(priorityString.equals(Priority.MAXIMUM.getDescription().toLowerCase())){
           return priority = Priority.MAXIMUM;
        }
        else if(priorityString.equals(Priority.MEAN.getDescription().toLowerCase())){
            return priority = Priority.MEAN;
        }
        else if(priorityString.equals(Priority.LOW.getDescription().toLowerCase())){
            return priority = Priority.LOW;
        }
        return null;
    }

//    public Note updateNote(NoteDTO noteDTO){
//
//    }

    public String validateDate(String date){
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
          return date;
        }
        else if(day <= dayAcutal && month > monthActual && year >= yearActual){
            return date;
        }
        else if(day <= dayAcutal && month <= monthActual && year > yearActual){
            return date;
        }
        throw new InvalidDateException();
    }
}
