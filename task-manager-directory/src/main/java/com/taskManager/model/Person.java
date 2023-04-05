package com.taskManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tb_noteList")
    private List<Note> noteList;

    public Person() {}

    public Person(Long id, String name, String email, String username, String password, List<Note> noteList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.noteList = noteList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(username, person.username) && Objects.equals(password, person.password) && Objects.equals(noteList, person.noteList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, username, password, noteList);
    }
}
