package com.example.piattaformamusicale.controller;

import com.example.piattaformamusicale.model.Brano;
import com.example.piattaformamusicale.repository.BranoRepository;
import com.example.piattaformamusicale.repository.GenereRepository;
import com.example.piattaformamusicale.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired private BranoRepository branoRepo;
    @Autowired private GenereRepository genereRepo;
    @Autowired private PlaylistService playlistService;

    @GetMapping(value = {"/", "/dashboard"})
    public String dashboard(Model model,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(required = false, defaultValue = "titolo") String filterType,
                            Principal principal) {

        List<Brano> risultati;

        if (keyword != null && !keyword.isEmpty()) {
            switch (filterType) {
                case "genere":
                    risultati = branoRepo.findByGenere_NomeContainingIgnoreCase(keyword);
                    break;
                case "autore":
                    risultati = branoRepo.findByAutoreNome(keyword);
                    break;
                case "titolo":
                default:
                    risultati = branoRepo.findByTitoloContainingIgnoreCase(keyword);
                    break;
            }
        } else {
            risultati = branoRepo.findAll();
        }

        model.addAttribute("activeFilter", filterType);

        model.addAttribute("brani", risultati);
        model.addAttribute("generi", genereRepo.findAll());

        if (principal != null) {
            model.addAttribute("miePlaylist", playlistService.getPlaylistsByUsername(principal.getName()));
        }

        return "dashboard";
    }
}