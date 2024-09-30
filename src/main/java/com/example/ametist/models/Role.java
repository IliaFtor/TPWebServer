package com.example.ametist.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment environment; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 
    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private Roles role; 
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    // Перечисление для ролей
    public enum Roles {
        CREATOR,
        MEMBER 
    }

    public Role() {
    }

    public Role(Environment environment, User user, Roles role, LocalDateTime createdTime) {
        this.environment = environment;
        this.user = user;
        this.role = role;
        this.createdTime = createdTime;
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}


