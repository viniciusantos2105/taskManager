package com.taskManager.service;

import com.taskManager.dto.LoginDTO;
import com.taskManager.dto.PersonDTO;
import com.taskManager.dto.TokenDTO;
import com.taskManager.exception.EmailAlreadyExistsException;
import com.taskManager.exception.PersonNotFoundException;
import com.taskManager.exception.UsernameInUseException;
import com.taskManager.exception.WrongPasswordException;
import com.taskManager.model.Person;
import com.taskManager.repository.PersonRepository;
import com.taskManager.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
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

    public Person registry(Person person){
        if(findByName(person.getUsername())){
           throw new UsernameInUseException();
        }
        if(findByEmail(person.getEmail())){
            throw new EmailAlreadyExistsException();
        }
        person.setNoteList(new ArrayList<>());
        return repository.save(person);
    }

    public Person updateEmail(PersonDTO personDTO){
        Person person = repository.findById(personDTO.getId()).orElseThrow(PersonNotFoundException::new);

    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Person encoderPassword(Person person){
        String password = passwordEncoder().encode(person.getPassword());
        person.setPassword(password);
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
        throw new WrongPasswordException();
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

    public boolean findByEmail(String email) { return repository.findByEmail(email).isPresent();}
}
