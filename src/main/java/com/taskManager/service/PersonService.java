package com.taskManager.service;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.model.Person;
import com.taskManager.repository.PersonRepository;
import com.taskManager.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonService implements UserDetailsService {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PersonRepository repository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public Person registry(PersonDTO personDTO){
        if(findByName(personDTO.getUsername())){
            throw new RuntimeException("Nome de usuario indisponivel");
        }
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setUsername(personDTO.getUsername());
        person.setPassword(passwordEncoder().encode(personDTO.getPassword()));
        person.setNoteList(new ArrayList<>());
        return repository.save(person);
    }

    public TokenDTO loginToken(LoginDTO login){
        Person person = Person.builder().username(login.getUsername())
                .password(login.getPassword()).build();
        UserDetails userLogin = authentic(person);
        return tokenProvider.createAccessToken(person.getUsername());
    }

    public UserDetails authentic(Person person){
        UserDetails usuario = loadUserByUsername(person.getUsername());
        boolean passwordEquals = passwordEncoder().matches(person.getPassword(), usuario.getPassword());
        if(passwordEquals){
            return usuario;
        }
        throw new RuntimeException("Senha inválida");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username inválido"));

        String[] roles = new String[]{"USER"};

        return User.builder().username(person.getUsername()).password(person.getPassword()).roles().build();
    }

    public boolean findByName(String username){
        return repository.findByUsername(username).isPresent();
    }
}
