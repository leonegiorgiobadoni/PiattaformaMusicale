package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autori")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String nome;


    @ManyToMany(mappedBy = "autori")
    @ToString.Exclude
    private List<Brano> brani = new ArrayList<>();
}