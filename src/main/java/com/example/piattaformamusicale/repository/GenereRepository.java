package com.example.piattaformamusicale.repository;


import com.example.piattaformamusicale.model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenereRepository extends JpaRepository<Genere, Integer> {
    Optional<Genere> findByNome(String nome); //impl future
}