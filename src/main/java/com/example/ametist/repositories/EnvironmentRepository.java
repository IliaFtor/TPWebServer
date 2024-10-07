package com.example.ametist.repositories;

import com.example.ametist.models.Environment;
import com.example.ametist.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer> {
    List<Environment> findByAuthorId(Integer authorId);
    Optional<Environment> findByAuthorAndName(User author, String name);
}

