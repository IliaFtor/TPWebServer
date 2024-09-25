package com.example.ametist.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "environments")
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @OneToMany(mappedBy = "environment")
    private List<Role> roles;

    @OneToMany(mappedBy = "environment")
    private List<Directory> directories;

    @OneToMany(mappedBy = "environment")
    private List<File> files;

    // Getters, Setters, Constructors
}