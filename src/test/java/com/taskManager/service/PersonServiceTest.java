package com.taskManager.service;

import com.taskManager.dto.PersonDTO;
import com.taskManager.mock.MockPerson;
import com.taskManager.model.Person;
import com.taskManager.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


}
