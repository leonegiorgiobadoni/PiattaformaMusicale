package com.example.piattaformamusicale.repository;


import com.example.piattaformamusicale.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.piattaformamusicale.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByUtente_Username(String username);
}