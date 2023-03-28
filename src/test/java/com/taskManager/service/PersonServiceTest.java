package com.taskManager.service;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.mock.MockPerson;
import com.taskManager.model.Person;
import com.taskManager.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PasswordEncoder encoder;

    @Mock
    private PersonRepository repository;

    @BeforeAll()
    void setUpMocks() throws Exception{
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistry(){
        Person entity = input.mockEntity();
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        Person pessoa = input.mockEntity();
        pessoa.setId(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.registry(pessoa);

        assertNotNull(result);
        assertNotNull(result.getName());
        assertNotNull(result.getUsername());
        assertNotNull(result.getEmail());

        assertEquals("Person username test1" ,result.getUsername());
        assertEquals("Person name test1", result.getName());
        assertEquals("Person email test1", result.getEmail());
        assertEquals("Password test1", result.getPassword());
        assertEquals(0, result.getNoteList().size());
    }

    @Test
    void testFindByUsername(){
        Person entity = input.mockEntity();
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        Person pessoa = input.mockEntity();
        pessoa.setId(1L);

        when(repository.findByUsername(entity.getUsername())).thenReturn(Optional.of(persisted));

        var result = service.findByUsername(pessoa.getUsername());

        assertTrue(result);
    }

    @Test
    void testFindByEmail(){
        Person entity = input.mockEntity();
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        Person pessoa = input.mockEntity();
        pessoa.setId(1L);

        when(repository.findByEmail(entity.getUsername())).thenReturn(Optional.of(persisted));

        var result = service.findByEmail(pessoa.getUsername());

        assertTrue(result);
    }

    @Test
    void testUpdateUsername(){
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonDTO vo = input.mockEntityDTO(1);
        vo.setId(1L);
        vo.setUsername("Username updated");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateUsername(vo);

        assertNotNull(result);
        assertEquals("Username updated" ,result.getUsername());
        assertEquals("Person name test1", result.getName());
        assertEquals("Person email test1", result.getEmail());
        assertEquals("Password test1", result.getPassword());
        assertEquals(0, result.getNoteList().size());
    }

    @Test
    void testUpdateEmail(){
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonDTO vo = input.mockEntityDTO(1);
        vo.setId(1L);
        vo.setEmail("Email updated");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.updateEmail(vo);

        assertNotNull(result);
        assertEquals("Person username test1" ,result.getUsername());
        assertEquals("Person name test1", result.getName());
        assertEquals("Email updated", result.getEmail());
        assertEquals("Password test1", result.getPassword());
        assertEquals(0, result.getNoteList().size());
    }

    @Test
    void testDelete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}
