package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_creazione", insertable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUtente", nullable = false)
    @ToString.Exclude
    private Utente utente;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ElementoPlaylist> elementi = new ArrayList<>();

    public int getDurataTotaleSecondi() {
        if (elementi == null || elementi.isEmpty()) {
            return 0;
        }
        return elementi.stream()
                .mapToInt(e -> e.getBrano().getDurataSecondi())
                .sum();
    }
}