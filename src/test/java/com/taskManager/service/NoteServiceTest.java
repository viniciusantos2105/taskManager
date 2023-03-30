package com.taskManager.service;

import com.taskManager.dto.NoteDTO;
import com.taskManager.enums.Priority;
import com.taskManager.enums.Situation;
import com.taskManager.mock.MockNote;
import com.taskManager.mock.MockPerson;
import com.taskManager.model.Note;
import com.taskManager.model.Person;
import com.taskManager.repository.NoteRepository;
import com.taskManager.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    MockNote input;

    MockPerson inputPerson;

    @InjectMocks
    private NoteService service;

    @Mock
    private PersonService personService;

    @Mock
    private NoteRepository repository;

    @Mock
    private PersonRepository personRepository;

    @BeforeAll()
    void setUpMocks() throws Exception{
        inputPerson = new MockPerson();
        input = new MockNote();
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreateNote(){
//        NoteDTO noteDTO = input.mockEntityDTO();
//        noteDTO.setId(1L);
//
//        Note entity = input.mockEntity();
//        entity.setId(1L);
//
//        Note persisted = entity;
//        persisted.setId(1L);
//
//        Person person = inputPerson.mockEntity();
//        person.setId(1L);
//
//        Person persistedPerson = person;
//        persistedPerson.setId(1L);
//
//        when(personService.findById(anyLong())).thenReturn(person);
//        when(repository.save(entity)).thenReturn(persisted);
//
//        var result = service.createNote(noteDTO);
//
//        assertNotNull(result);
//    }

    @Test
    void testUpdateNotePriotiry(){
        Note note = input.mockEntity();

        Note persisted = note;
        persisted.setId(1L);

        NoteDTO noteDTO = input.mockEntityDTO();
        noteDTO.setId(1L);
        noteDTO.setPriority("baixa");


        when(repository.findById(1L)).thenReturn(Optional.of(note));
        when(repository.save(note)).thenReturn(persisted);

        var result = service.updateNotePriotiry(noteDTO);

        assertNotNull(result);
        assertEquals("Title test1", result.getTitle());
        assertEquals("Description test1", result.getDescription());
        assertEquals("29/12/2023 test1", result.getDate());
        assertEquals(Situation.OPENED, result.getSituation());
        assertEquals(Priority.LOW, result.getPriority());
    }


    @Test
    void testUpdateNoteSituation(){
        Note note = input.mockEntity();

        Note persisted = note;
        persisted.setId(1L);

        NoteDTO noteDTO = input.mockEntityDTO();
        noteDTO.setId(1L);
        noteDTO.setSituation("feito");


        when(repository.findById(1L)).thenReturn(Optional.of(note));
        when(repository.save(note)).thenReturn(persisted);

        var result = service.updateNoteSituation(noteDTO);

        assertNotNull(result);
        assertEquals("Title test1", result.getTitle());
        assertEquals("Description test1", result.getDescription());
        assertEquals("29/12/2023 test1", result.getDate());
        assertEquals(Priority.MAXIMUM, result.getPriority());
        assertEquals(Situation.DONE, result.getSituation());
    }

    @Test
    void testFindAll(){
        List<Note> noteList = input.mockEntityList();

        Person person = inputPerson.mockEntity();
        person.setId(1L);
        person.setNoteList(noteList);

        when(personService.findById(anyLong())).thenReturn(person);

        var result = service.findAll(1L);

        assertNotNull(result);

        var result0 = result.get(0);

        assertNotNull(result);
        assertEquals("Title test0", result0.getTitle());
        assertEquals("Description test0", result0.getDescription());
        assertEquals("29/12/2023 test0", result0.getDate());
        assertEquals(Situation.OPENED, result0.getSituation());
        assertEquals(Priority.MAXIMUM, result0.getPriority());

        var result6 = result.get(6);

        assertNotNull(result);
        assertEquals("Title test6", result6.getTitle());
        assertEquals("Description test6", result6.getDescription());
        assertEquals("29/12/2023 test6", result6.getDate());
        assertEquals(Situation.OPENED, result6.getSituation());
        assertEquals(Priority.MAXIMUM, result6.getPriority());

        var result13 = result.get(13);

        assertNotNull(result);
        assertEquals("Title test13", result13.getTitle());
        assertEquals("Description test13", result13.getDescription());
        assertEquals("29/12/2023 test13", result13.getDate());
        assertEquals(Situation.OPENED, result13.getSituation());
        assertEquals(Priority.MAXIMUM, result13.getPriority());
    }
}
