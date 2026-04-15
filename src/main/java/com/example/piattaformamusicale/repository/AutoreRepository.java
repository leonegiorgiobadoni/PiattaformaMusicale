package com.example.piattaformamusicale.repository;


import com.example.piattaformamusicale.model.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, Integer> {

    Optional<Autore> findByNome(String nome);

}