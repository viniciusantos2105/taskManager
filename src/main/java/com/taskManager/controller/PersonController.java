package com.taskManager.controller;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.model.Person;
import com.taskManager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/registry")
    public Person registry(@RequestBody PersonDTO personDTO){
        return service.registry(personDTO);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO){
        return service.loginToken(loginDTO);
    }
}
