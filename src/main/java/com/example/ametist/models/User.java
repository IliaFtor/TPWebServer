package com.example.ametist.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String username;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "author")
    private List<Environment> environments;

    // Поле roles должно быть коллекцией
    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    public User() {}

    public User(Integer id, String name){
        this.username = name;
        this.id = id;
    }
    public User(String name, String email) {
        this.username = name;
        this.mail = email;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatedTime(LocalDateTime Time) {
        this.createdTime = Time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHashPassword() {
        return passwordHash;
    }

    public String getName() {
        return username;
    }

    public void setHashPassword(String pass) {
        this.passwordHash = pass;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() {
        return mail;
    }

    public void setHashPasswoed(String password) {
        this.passwordHash = password;
    }

    public void setEmail(String email) {
        this.mail = email;
    }

    // Метод для установки ролей
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // Метод для получения ролей
    public List<Role> getRoles() {
        return roles;
    }
}

