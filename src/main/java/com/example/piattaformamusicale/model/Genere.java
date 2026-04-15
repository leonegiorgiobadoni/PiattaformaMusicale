package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @OneToMany(mappedBy = "genere")
    @ToString.Exclude // evita loop infiniti
    private List<Brano> brani = new ArrayList<>();
}