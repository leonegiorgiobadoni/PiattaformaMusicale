package com.example.piattaformamusicale.service;

import com.example.piattaformamusicale.model.Role;
import com.example.piattaformamusicale.model.Utente;
import com.example.piattaformamusicale.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired private UtenteRepository utenteRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    // registrazione oubblica
    public void registraUtente(Utente utente) {
        if (utenteRepo.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username già esistente");
        }
        if (utenteRepo.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        utente.setRuolo(Role.USER);

        utente.setAutenticato(false);

        utenteRepo.save(utente);
    }

    // creazione da admin
    public void creaUtenteDaAdmin(Utente utente) {
        if (utenteRepo.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username già esistente");
        }
        if (utenteRepo.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));


        utente.setAutenticato(true);

        if (utente.getRuolo() == null) {
            utente.setRuolo(Role.USER);
        }

        utenteRepo.save(utente);
    }

    // accetta richiesta
    public void approvaUtente(Integer id) {
        Utente utente = utenteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + id));


        utente.setAutenticato(true);

        utenteRepo.save(utente);
    }
}