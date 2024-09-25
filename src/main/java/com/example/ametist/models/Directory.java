package com.example.ametist.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "directories")
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "enviroment", nullable = false)
    private Environment environment;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Directory parent;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Directory> subdirectories;

    @OneToMany(mappedBy = "directory")
    private List<File> files;

    // Getters, Setters, Constructors
}