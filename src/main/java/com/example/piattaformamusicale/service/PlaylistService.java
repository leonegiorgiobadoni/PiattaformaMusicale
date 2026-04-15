package com.example.piattaformamusicale.service;

import com.example.piattaformamusicale.model.Brano;
import com.example.piattaformamusicale.model.ElementoPlaylist;
import com.example.piattaformamusicale.model.Playlist;
import com.example.piattaformamusicale.model.Utente;
import com.example.piattaformamusicale.repository.BranoRepository;
import com.example.piattaformamusicale.repository.ElementoPlaylistRepository;
import com.example.piattaformamusicale.repository.PlaylistRepository;
import com.example.piattaformamusicale.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired private PlaylistRepository playlistRepo;
    @Autowired private BranoRepository branoRepo;
    @Autowired private ElementoPlaylistRepository elementoRepo;
    @Autowired private UtenteRepository utenteRepo;

    private static final int MAX_DURATA_SECONDI = 7200;

    public List<Playlist> getPlaylistsByUsername(String username) {
        return playlistRepo.findByUtente_Username(username);
    }

    public Playlist getPlaylistById(Integer id, String username) {
        Playlist p = playlistRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist non trovata"));

        if (!p.getUtente().getUsername().equals(username)) {
            throw new SecurityException("Non sei il proprietario di questa playlist");
        }

        List<ElementoPlaylist> elementiOrdinati = elementoRepo.findByPlaylist_IdOrderByPosizioneAsc(id);

        p.setElementi(elementiOrdinati);

        return p;
    }

    @Transactional
    public void creaPlaylist(String nome, String username) {
        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Playlist p = new Playlist();
        p.setNome(nome);
        p.setUtente(utente);
        p.setDataCreazione(LocalDateTime.now());
        playlistRepo.save(p);
    }

    @Transactional
    public void eliminaPlaylist(Integer playlistId, String username) {
        Playlist p = checkProprieta(playlistId, username);
        playlistRepo.delete(p);
    }

    @Transactional
    public void rinominaPlaylist(Integer playlistId, String nuovoNome, String username) {
        Playlist p = checkProprieta(playlistId, username);
        p.setNome(nuovoNome);
        playlistRepo.save(p);
    }

    private Playlist checkProprieta(Integer playlistId, String username) {
        Playlist p = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist non trovata"));
        if (!p.getUtente().getUsername().equals(username)) {
            throw new SecurityException("Non hai i permessi per modificare questa playlist");
        }
        return p;
    }

    @Transactional
    public void aggiungiBrano(Integer playlistId, Integer branoId) {
        Playlist playlist = playlistRepo.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist non trovata"));

        Brano brano = branoRepo.findById(branoId)
                .orElseThrow(() -> new IllegalArgumentException("Brano non trovato"));

        int durataAttuale = playlist.getDurataTotaleSecondi();

        if (durataAttuale + brano.getDurataSecondi() > MAX_DURATA_SECONDI) {
            throw new RuntimeException("Limite superato! La playlist non può eccedere le 2 ore.");
        }

        Integer nuovaPosizione = elementoRepo.findFirstByPlaylistIdOrderByPosizioneDesc(playlistId)
                .map(elemento -> elemento.getPosizione() + 1)
                .orElse(1);

        ElementoPlaylist nuovoElemento = new ElementoPlaylist();
        nuovoElemento.setPlaylist(playlist);
        nuovoElemento.setBrano(brano);
        nuovoElemento.setPosizione(nuovaPosizione);
        nuovoElemento.setDataAggiunta(java.time.LocalDateTime.now());

        elementoRepo.save(nuovoElemento);
    }

    @Transactional
    public void rimuoviElementoDaPlaylist(Integer elementoId, String username) {
        ElementoPlaylist elementoDaRimuovere = elementoRepo.findById(elementoId)
                .orElseThrow(() -> new IllegalArgumentException("Elemento non trovato"));

        if (!elementoDaRimuovere.getPlaylist().getUtente().getUsername().equals(username)) {
            throw new SecurityException("Non sei il proprietario di questa playlist");
        }

        Integer idPlaylist = elementoDaRimuovere.getPlaylist().getId();

        elementoRepo.delete(elementoDaRimuovere);

        elementoRepo.flush();

        List<ElementoPlaylist> elementiRimasti = elementoRepo.findByPlaylist_IdOrderByPosizioneAsc(idPlaylist);

        //"riordina brani" se eliminato brano 3, il brano 4 assume posizione 3
        int nuovoContatore = 1;
        for (ElementoPlaylist elemento : elementiRimasti) {
            if (elemento.getPosizione() != nuovoContatore) {
                elemento.setPosizione(nuovoContatore);
            }
            nuovoContatore++;
        }

        elementoRepo.saveAll(elementiRimasti);
    }
}