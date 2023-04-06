package com.taskManager.service;

import com.taskManager.dto.NoteDTO;
import com.taskManager.enums.Priority;
import com.taskManager.enums.Situation;
import com.taskManager.exception.InvalidDateException;
import com.taskManager.exception.NoteNotFoundException;
import com.taskManager.exception.PersonNotFoundException;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.repository.NoteRepository;
import com.taskManager.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private PersonService personService;

    @Autowired
    private NoteRepository repository;

    @Autowired
    private PersonRepository personRepository;

    public Note createNote(NoteDTO noteDTO){
        Person person = personService.findById(noteDTO.getPersonId());

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
        if(noteDTO.getSituation().equals("em aberto")){
            note.setSituation(Situation.OPENED);
            return repository.save(note);
        }
        return note;
    }

    public Note findById(Long idPerson, Long idNote){
        Person person = personService.findById(idPerson);
        Note note = repository.findById(idNote).orElseThrow(NoteNotFoundException::new);
        int i = person.getNoteList().size();
        i--;
        if(person.getNoteList().isEmpty()){
            throw new NoteNotFoundException();
        }

        for(i = i; i >= 0; i--){

          Note validate = person.getNoteList().get(i);
            if(validate.equals(note)){
                return validate;
            }
        }

        throw new NoteNotFoundException();
    }

    public List<Note> findAll(Long id){
        Person person = personService.findById(id);
        return person.getNoteList();
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

    public void deleteNote(Long idPerson, Long idNote){
        Note note = findById(idPerson, idNote);
        repository.delete(note);
    }

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
