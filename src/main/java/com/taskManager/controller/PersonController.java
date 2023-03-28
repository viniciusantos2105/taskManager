package com.taskManager.controller;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.model.Person;
import com.taskManager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/registry")
    public Person registry(@RequestBody Person personDTO){
        Person person = service.registry(personDTO);
        return service.encoderPassword(person);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO){
        return service.loginToken(loginDTO);
    }

    @PatchMapping("/update/username")
    public Person updateUsername(@RequestBody PersonDTO personDTO){
        return service.updateUsername(personDTO);
    }

    @PatchMapping("/update/email")
    public Person updateEmail(@RequestBody PersonDTO personDTO){
        return service.updateEmail(personDTO);
    }

    @PatchMapping("/update/password")
    public Person updatePassword(@RequestBody PersonDTO personDTO){ return service.updateEmail(personDTO); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
