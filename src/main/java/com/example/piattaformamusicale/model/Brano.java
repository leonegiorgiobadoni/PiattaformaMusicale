package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "brani")
@Data
public class Brano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titolo;

    @Column(name = "durata_secondi")
    private Integer durataSecondi;

    @Column(length = 20)
    private String lingua;

    @Column(name = "data_pubblicazione")
    private java.time.LocalDate dataPubblicazione;

    @Column(name = "link_audio_mp4")
    private String linkAudioMp4;

    @Column(name = "link_youtube")
    private String linkYoutube;

    @ManyToOne
    @JoinColumn(name = "idGenere")
    private Genere genere;

    @ManyToMany
    @JoinTable(
            name = "brani_autori",
            joinColumns = @JoinColumn(name = "idBrano"),
            inverseJoinColumns = @JoinColumn(name = "idAutore")
    )
    private List<Autore> autori;

    public String getAutoriString() {
        if (autori == null || autori.isEmpty()) {
            return "Sconosciuto";
        }
        return autori.stream()
                .map(Autore::getNome)
                .collect(Collectors.joining(", "));
    }

    public String getDurataFormattata() {
        if (durataSecondi == null) return "0:00";
        int minuti = durataSecondi / 60;
        int secondi = durataSecondi % 60;
        return String.format("%d:%02d", minuti, secondi);
    }
}