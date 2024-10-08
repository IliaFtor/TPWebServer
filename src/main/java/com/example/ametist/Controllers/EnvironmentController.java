package com.example.ametist.Controllers;

import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Directory;
import com.example.ametist.models.Environment;
import com.example.ametist.models.User;
import com.example.ametist.repositories.SimplifiedEnvironmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentController {

    private final My_En_serv environmentService;

    public EnvironmentController(My_En_serv environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getEnvironmentByUsername(@PathVariable String username) {
        Optional<User> optionalUser = environmentService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User author = optionalUser.get();
        List<Environment> environments = environmentService.getEnvironmentsByAuthorId(author.getId());

        return createResponse(author, environments);
    }

    private ResponseEntity<EnvironmentResponse> createResponse(User author, List<Environment> environments) {
        if (environments.isEmpty()) {
            return createEmptyResponse(author);
        } else {
            return createEnvironmentResponse(author, environments);
        }
    }

    private ResponseEntity<EnvironmentResponse> createEmptyResponse(User author) {
        // Создаем упрощенное представление
        SimplifiedEnvironmentResponse simplifiedView = new SimplifiedEnvironmentResponse(author.getId(), author.getName());

        // Создаем пустой список директорий
        List<Directory> emptyDirectories = new ArrayList<>();

        return new ResponseEntity<>(new EnvironmentResponse(simplifiedView, emptyDirectories), HttpStatus.OK);
    }

    private ResponseEntity<EnvironmentResponse> createEnvironmentResponse(User author, List<Environment> environments) {
        // Берем первое окружение
        Environment env = environments.get(0);

        // Создаем упрощенное представление
        SimplifiedEnvironmentResponse simplifiedView = new SimplifiedEnvironmentResponse(author.getId(), author.getName());

        // Получаем список директорий
        List<Directory> directories = env.getDirectories();

        return new ResponseEntity<>(new EnvironmentResponse(simplifiedView, directories), HttpStatus.OK);
    }

    // Класс для формирования ответа с упрощенным представлением и списком директорий
    static class EnvironmentResponse {
        private final SimplifiedEnvironmentResponse simplifiedView;
        private final List<Directory> directories;

        public EnvironmentResponse(SimplifiedEnvironmentResponse simplifiedView, List<Directory> directories) {
            this.simplifiedView = simplifiedView;
            this.directories = directories;
        }

        public SimplifiedEnvironmentResponse getSimplifiedView() {
            return simplifiedView;
        }

        public List<Directory> getDirectories() {
            return directories;
        }
    }
}
