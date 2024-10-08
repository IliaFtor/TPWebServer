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
    @JoinColumn(name = "environment", nullable = false)
    private Environment environment;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Directory parent;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "directory")
    private List<File> files;

    @OneToMany(mappedBy = "parent")
    private List<Directory> subdirectories;

    // Constructors, Getters, and Setters

    public Directory(String name, Environment environment, Directory parent) {
        this.name = name;
        this.environment = environment;
        this.parent = parent;
    }

    // Overloaded constructor without environment
    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }
    public Directory() {}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    // This method now returns the list of files in this directory
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Directory> getSubdirectories() {
        return subdirectories;
    }

    public void setSubdirectories(List<Directory> subdirectories) {
        this.subdirectories = subdirectories;
    }
    
}