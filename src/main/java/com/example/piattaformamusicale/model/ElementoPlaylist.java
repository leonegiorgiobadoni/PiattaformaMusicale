package com.example.piattaformamusicale.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "elementi_playlist",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"idPlaylist", "posizione"}),
                @UniqueConstraint(columnNames = {"idPlaylist", "idBrano"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementoPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlaylist", nullable = false)
    @ToString.Exclude
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idBrano", nullable = false)
    @ToString.Exclude
    private Brano brano;

    @Column(nullable = false)
    private Integer posizione;

    @Column(name = "data_aggiunta", insertable = false, updatable = false)
    private LocalDateTime dataAggiunta;
}