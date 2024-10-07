package com.example.ametist.Controllers;
import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Directory;
import com.example.ametist.models.Environment;
import com.example.ametist.models.File;
import com.example.ametist.repositories.DirectoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*{
    "files": [
        {
            "id": 1,
            "name": "file1.txt",
            "content": "some content",
            "lastUpdate": "2023-10-03T12:34:56"
        },
        {
            "id": 2,
            "name": "file2.txt",
            "content": "some other content",
            "lastUpdate": "2023-10-03T13:45:56"
        } */
@RestController
@RequestMapping("/api/directories")
public class DirectoryController {

    @Autowired
    private DirectoryRepository directoryRepository;

    @GetMapping("/{id}/contents")
    public ResponseEntity<?> getDirectoryContents(@PathVariable Integer id) {
        // Находим директорию по ID
        Optional<Directory> directoryOptional = directoryRepository.findById(id);

        if (directoryOptional.isPresent()) {
            Directory directory = directoryOptional.get();

            // Получаем файлы и подпапки
            List<File> files = directory.getFiles();
            List<Directory> subdirectories = directoryRepository.findByParent(directory);

            // Формируем ответ
            Map<String, Object> response = new HashMap<>();
            response.put("files", files);
            response.put("subdirectories", subdirectories);

            return ResponseEntity.ok(response);
        } else {
            // Если директория не найдена
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Directory not found");
        }
    }
}

