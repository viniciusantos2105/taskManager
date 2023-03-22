package com.taskManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
