package com.example.ametist.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "enviroments", nullable = false)
    private Environment environment;

    @ManyToOne
    @JoinColumn(name = "directory")
    private Directory directory;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // Getters, Setters, Constructors
}
