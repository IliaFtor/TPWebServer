package com.example.ametist;

import com.example.ametist.models.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer> {
    List<Environment> findByAuthorId(Integer authorId);
}

