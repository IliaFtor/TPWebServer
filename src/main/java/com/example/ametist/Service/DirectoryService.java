package com.example.ametist.Service;

import com.example.ametist.models.Directory;
import com.example.ametist.models.Environment;
import com.example.ametist.repositories.DirectoryRepository;
import com.example.ametist.repositories.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectoryService {

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private EnvironmentRepository environmentRepository; // Assuming you have this repository

    public Directory createDirectory(String name, Integer parentId) {
        Directory parentDirectory = null;

        if (parentId != null) {
            Optional<Directory> parentOptional = directoryRepository.findById(parentId);
            if (parentOptional.isPresent()) {
                parentDirectory = parentOptional.get();
            } else {
                throw new RuntimeException("Parent directory not found");
            }
        }

        // Create the new directory
        Directory newDirectory = new Directory(name, /* your environment here */ parentDirectory);
        return directoryRepository.save(newDirectory);
    }
}
