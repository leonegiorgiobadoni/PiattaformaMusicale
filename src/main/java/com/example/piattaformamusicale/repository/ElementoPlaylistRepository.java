package com.example.piattaformamusicale.repository;

import com.example.piattaformamusicale.model.ElementoPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementoPlaylistRepository extends JpaRepository<ElementoPlaylist, Integer> {

    List<ElementoPlaylist> findByPlaylist_IdOrderByPosizioneAsc(Integer idPlaylist);
    Optional<ElementoPlaylist> findFirstByPlaylistIdOrderByPosizioneDesc(Integer playlistId);
}