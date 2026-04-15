package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity @Table(name = "utenti") @Data
public class Utente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role ruolo;

    private boolean autenticato;
}
