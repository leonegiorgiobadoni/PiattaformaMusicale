package com.example.piattaformamusicale.controller;

import com.example.piattaformamusicale.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public String visualizzaPlaylist(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("playlists", playlistService.getPlaylistsByUsername(username));
        return "playlist/list";
    }


    @GetMapping("/{id}")
    public String dettaglioPlaylist(@PathVariable Integer id, Model model, Principal principal) {
        try {
            model.addAttribute("playlist", playlistService.getPlaylistById(id, principal.getName()));
            return "playlist/detail";
        } catch (Exception e) {
            return "redirect:/playlists";
        }
    }

    @PostMapping("/create")
    public String creaPlaylist(@RequestParam String nome, Principal principal) {
        playlistService.creaPlaylist(nome, principal.getName());
        return "redirect:/playlists";
    }

    @PostMapping("/delete")
    public String eliminaPlaylist(@RequestParam Integer id, Principal principal) {
        try {
            playlistService.eliminaPlaylist(id, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/playlists";
    }

    @PostMapping("/rename")
    public String rinominaPlaylist(@RequestParam Integer id, @RequestParam String nuovoNome, Principal principal) {
        try {
            playlistService.rinominaPlaylist(id, nuovoNome, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/playlists";
    }

    @PostMapping("/add-brano")
    public String addBrano(@RequestParam Integer playlistId, @RequestParam Integer branoId) {
        try {
            playlistService.aggiungiBrano(playlistId, branoId);
            return "redirect:/?success=brano_aggiunto";
        } catch (Exception e) {
            return "redirect:/?error=errore_aggiunta";
        }
    }

    @PostMapping("/remove-brano")
    public String rimuoviBrano(@RequestParam Integer elementoId, @RequestParam Integer playlistId, Principal principal) {
        try {
            playlistService.rimuoviElementoDaPlaylist(elementoId, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/playlists/" + playlistId;
    }
}