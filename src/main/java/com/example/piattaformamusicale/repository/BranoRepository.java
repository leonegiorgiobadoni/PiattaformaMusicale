package com.example.piattaformamusicale.repository;


import com.example.piattaformamusicale.model.Brano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranoRepository extends JpaRepository<Brano, Integer> {

    List<Brano> findByTitoloContainingIgnoreCase(String keyword);

    List<Brano> findByGenere_NomeContainingIgnoreCase(String nomeGenere);

    @Query("SELECT b FROM Brano b JOIN b.autori a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nomeAutore, '%'))")
    List<Brano> findByAutoreNome(@Param("nomeAutore") String nomeAutore);
}