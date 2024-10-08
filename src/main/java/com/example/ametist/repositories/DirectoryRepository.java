package com.example.ametist.repositories;

import com.example.ametist.models.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DirectoryRepository extends JpaRepository<Directory, Integer> {
    Optional<Directory> findById(Integer id);

    List<Directory> findByParent(Directory parent);
}

