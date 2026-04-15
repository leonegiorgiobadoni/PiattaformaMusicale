package com.example.piattaformamusicale.controller;

import com.example.piattaformamusicale.model.Autore;
import com.example.piattaformamusicale.model.Brano;
import com.example.piattaformamusicale.model.Role;
import com.example.piattaformamusicale.model.Utente;
import com.example.piattaformamusicale.repository.AutoreRepository;
import com.example.piattaformamusicale.repository.BranoRepository;
import com.example.piattaformamusicale.repository.GenereRepository;
import com.example.piattaformamusicale.repository.UtenteRepository;
import com.example.piattaformamusicale.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class AdminController {

    @Autowired private BranoRepository branoRepo;
    @Autowired private UtenteRepository utenteRepo;
    @Autowired private GenereRepository genereRepo;
    @Autowired private AutoreRepository autoreRepo;
    @Autowired private UtenteService utenteService;

    @GetMapping("/panel")
    public String adminPanel() {
        return "admin/panel";
    }

    @GetMapping("/brani/new")
    public String formNuovoBrano(Model model) {
        model.addAttribute("brano", new Brano());
        model.addAttribute("generi", genereRepo.findAll());
        model.addAttribute("autoriList", autoreRepo.findAll());
        return "admin/brano-form";
    }

    @PostMapping("/brani/save")
    public String salvaBrano(@ModelAttribute Brano brano,
                             @RequestParam("fileAudio") MultipartFile multipartFile,
                             @RequestParam(value = "nuovoAutoreNome", required = false) String nuovoAutoreNome) throws IOException {

        // logica nuovo autore
        if (nuovoAutoreNome != null && !nuovoAutoreNome.trim().isEmpty()) {
            Autore autoreDaAggiungere = autoreRepo.findByNome(nuovoAutoreNome.trim())
                    .orElseGet(() -> {
                        Autore nuovo = new Autore();
                        nuovo.setNome(nuovoAutoreNome.trim());
                        return autoreRepo.save(nuovo);
                    });

            if (brano.getAutori() == null) {
                brano.setAutori(new ArrayList<>());
            }
            brano.getAutori().add(autoreDaAggiungere);
        }

        // logica upload file mp3
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "./uploads";

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                // salva nel db il percorso che verrà usato dal browser per riprodurre l'audio
                brano.setLinkAudioMp4("/audio/" + fileName);
            } catch (IOException ioe) {
                throw new IOException("Errore nel salvataggio del file: " + fileName, ioe);
            }
        }

        branoRepo.save(brano);
        return "redirect:/admin/panel";
    }

    @PostMapping("/brani/delete/{id}")
    public String eliminaBrano(@PathVariable Integer id) {
        branoRepo.deleteById(id);
        return "redirect:/admin/panel";
    }

    @GetMapping("/utenti")
    public String listaUtenti(Model model) {
        model.addAttribute("utenti", utenteRepo.findAll());
        return "admin/utenti-list";
    }

    @GetMapping("/utenti/new")
    public String formNuovoUtente(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("ruoli", Role.values());
        return "admin/utente-form";
    }

    @PostMapping("/utenti/save")
    public String salvaUtente(@ModelAttribute Utente utente) {
        try {
            utenteService.creaUtenteDaAdmin(utente);
        } catch (RuntimeException e) {
            return "redirect:/admin/utenti?error=" + e.getMessage();
        }
        return "redirect:/admin/utenti";
    }

    @PostMapping("/utenti/delete/{id}")
    public String eliminaUtente(@PathVariable Integer id) {
        utenteRepo.deleteById(id);
        return "redirect:/admin/utenti";
    }

    @GetMapping("/richieste")
    public String listaRichieste(Model model) {
        //mostra solo gli utenti con autenticato=false
        model.addAttribute("richieste", utenteRepo.findByAutenticatoFalse());
        return "admin/richieste-list";
    }

    @PostMapping("/richieste/accept/{id}")
    public String accettaUtente(@PathVariable Integer id) {
        utenteService.approvaUtente(id);
        return "redirect:/admin/richieste";
    }

    @PostMapping("/richieste/reject/{id}")
    public String rifiutaUtente(@PathVariable Integer id) {
        utenteRepo.deleteById(id);
        return "redirect:/admin/richieste";
    }
}