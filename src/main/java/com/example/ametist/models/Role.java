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
    @JoinColumn(name = "enviroment", nullable = false)
    private Environment environment;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(nullable = false)
    private Roles role; //энам гэ

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;//костыль тут хз что за тип 

    public enum Roles{
        Creater,
        member
    }

    public void setRole(Roles role){
        this.role = role;
    }
}

