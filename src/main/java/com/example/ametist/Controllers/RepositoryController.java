package com.example.ametist.Controllers;

import com.example.ametist.models.Directory;
import com.example.ametist.models.Environment;
import com.example.ametist.models.File;
import com.example.ametist.models.User;
import com.example.ametist.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/* 
{
    "id": 1,
    "author": {
        "id": 2,
        "username": "Ilia",
        "email": "esl.com"
    },
    "name": "my",
    "isPublic": true
}
*/
@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final UserRepository userRepository;
    private final EnvironmentRepository environmentRepository;
    private final DirectoryRepository directoryRepository;
    private final FileRepository fileRepository;

    public RepositoryController(UserRepository userRepository, 
                                EnvironmentRepository environmentRepository, 
                                DirectoryRepository directoryRepository, 
                                FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.environmentRepository = environmentRepository;
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    // Получение информации о репозитории (окружении) по автору и имени репозитория
    @GetMapping("/by-author-and-name")
    public ResponseEntity<?> getRepositoryByAuthorAndName(
            @RequestParam String authorName, 
            @RequestParam String repositoryName) {

        // Поиск пользователя по имени
        Optional<User> author = userRepository.findByUsername(authorName);
        if (author.isEmpty()) {
            return ResponseEntity.badRequest().body("Author not found");
        }

        // Поиск окружения (репозитория) по имени и автору
        Optional<Environment> environment = environmentRepository.findByAuthorAndName(author.get(), repositoryName);
        if (environment.isEmpty()) {
            return ResponseEntity.badRequest().body("Repository not found");
        }

        return ResponseEntity.ok(environment.get());
    }

    // Получение содержимого папки (директории) по её id
    @GetMapping("/directories/{directoryId}/contents")
    public ResponseEntity<?> getDirectoryContents(@PathVariable Integer directoryId) {
        // Поиск папки (директории) по id
        Optional<Directory> directory = directoryRepository.findById(directoryId);
        if (directory.isEmpty()) {
            return ResponseEntity.badRequest().body("Directory not found");
        }

        // Получение поддиректорий и файлов внутри данной папки
        List<Directory> subDirectories = directoryRepository.findByParent(directory.get());
        List<File> files = fileRepository.findByDirectory(directory.get());

        return ResponseEntity.ok(new DirectoryContentsResponse(subDirectories, files));
    }

    // Вспомогательный класс для содержимого папки
    public static class DirectoryContentsResponse {
        private final List<Directory> subDirectories;
        private final List<File> files;

        public DirectoryContentsResponse(List<Directory> subDirectories, List<File> files) {
            this.subDirectories = subDirectories;
            this.files = files;
        }

        public List<Directory> getSubDirectories() {
            return subDirectories;
        }

        public List<File> getFiles() {
            return files;
        }
    }
}



