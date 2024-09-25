package com.example.ametist.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ametist.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByMail(String email);
}
