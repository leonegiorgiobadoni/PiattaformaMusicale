package com.example.piattaformamusicale.config;

import com.example.piattaformamusicale.model.Genere;
import com.example.piattaformamusicale.model.Role;
import com.example.piattaformamusicale.model.Utente;
import com.example.piattaformamusicale.repository.GenereRepository;
import com.example.piattaformamusicale.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired private UtenteRepository utenteRepo;
    @Autowired private GenereRepository genereRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (!utenteRepo.existsByUsername("admin")) {
            Utente admin = new Utente();
            admin.setUsername("admin");
            admin.setEmail("admin@music.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRuolo(Role.SUPER_ADMIN);
            admin.setAutenticato(true);

            utenteRepo.save(admin);
            System.out.println("Utente admin creato: username='admin', password='admin123'");
        }

        if (genereRepo.count() == 0) {
            List<String> nomiGeneri = Arrays.asList(
                    "Rock", "Pop", "Jazz", "Blues", "Metal",
                    "Hip Hop", "Rap", "Trap","Musica Classica", "Elettronica", "Reggae"
            );

            for (String nome : nomiGeneri) {
                Genere g = new Genere();
                g.setNome(nome);
                genereRepo.save(g);
            }

            System.out.println("Database inizializzato con " + nomiGeneri.size() + " generi musicali.");
        } else {
            System.out.println("I generi sono già presenti nel database.");
        }
    }
}