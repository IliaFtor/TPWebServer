package com.example.ametist.repositories;

import com.example.ametist.models.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DirectoryRepository extends JpaRepository<Directory, Integer> {
    List<Directory> findByParent(Directory parent);
    
}

