package com.example.ametist.Controllers;

import com.example.ametist.Service.DirectoryService;  // Import the DirectoryService
import com.example.ametist.models.Directory;
import com.example.ametist.models.File;
import com.example.ametist.repositories.DirectoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/directories")
public class DirectoryController {
    @Autowired
    private DirectoryRepository directoryRepository;

    @PostMapping
    public ResponseEntity<Directory> createDirectory(@RequestBody CreateDirectoryRequest request) {
        Directory parentDirectory = null;
        if (request.getParentId() != null) {
            parentDirectory = directoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent directory not found"));
        }

        Directory createdDirectory = new Directory();
        createdDirectory.setName(request.getName());
        createdDirectory.setParent(parentDirectory);

        createdDirectory = directoryRepository.save(createdDirectory);
        return ResponseEntity.ok(createdDirectory);
    }
    

    @GetMapping("/{id}/contents")
    public ResponseEntity<?> getDirectoryContents(@PathVariable Integer id) {
        // Находим директорию по ID
        Optional<Directory> directoryOptional = directoryRepository.findById(id);

        if (directoryOptional.isPresent()) {
            Directory directory = directoryOptional.get();

            // Получаем файлы и подпапки
            List<File> files = directory.getFiles(); // Ensure this method exists in your Directory model
            List<Directory> subdirectories = directoryRepository.findByParent(directory); // This will now work

            // Формируем ответ в виде JSON
            Map<String, Object> response = new HashMap<>();
            response.put("directory", directory);
            response.put("files", files);
            response.put("subdirectories", subdirectories);

            // Возвращаем ответ в формате JSON
            return ResponseEntity.ok(response);
        } else {
            // Если директория не найдена, возвращаем JSON с пустыми полями
            Map<String, Object> emptyResponse = new HashMap<>();
            emptyResponse.put("directory", null); // или new Directory() для пустого объекта
            emptyResponse.put("files", new ArrayList<>()); // Пустой список файлов
            emptyResponse.put("subdirectories", new ArrayList<>()); // Пустой список подкаталогов

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyResponse);
        }
    }

    public static class CreateDirectoryRequest {
        private String name;
        private Integer parentId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }
    }
}
