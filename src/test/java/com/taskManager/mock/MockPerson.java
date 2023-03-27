package com.taskManager.mock;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.model.Person;

import java.util.ArrayList;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(1);
    }

    public PersonDTO mockEntityDTO() {
        return mockEntityDTO(1);
    }


    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setName("Person name test" + number);
        person.setUsername("Person username test" + number);
        person.setEmail("Person email test" + number);
        person.setPassword("Password test" + number);
        person.setNoteList(new ArrayList<>());
        return person;
    }

    public PersonDTO mockEntityDTO(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setName("Person name test" + number);
        person.setUsername("Person username test" + number);
        person.setEmail("Person email test" + number);
        person.setPassword("Password test" + number);
        return person;
    }


}
