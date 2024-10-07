package com.example.ametist.repositories;

import com.example.ametist.models.Directory;
import com.example.ametist.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findByDirectory(Directory directory);
}
