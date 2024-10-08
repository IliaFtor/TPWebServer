package com.example.ametist.Controllers;

import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Environment;
import com.example.ametist.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentController {

    private final My_En_serv environmentService;

    @Autowired
    public EnvironmentController(My_En_serv environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<Object> getEnvironmentByUsername(@PathVariable String username) {
        // Получаем пользователя по имени
        Optional<User> optionalUser = environmentService.findUserByUsername(username);

        // Если пользователь не найден, обрабатываем соответствующим образом (например, возвращаем 404)
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND); // Или бросить исключение
        }

        User author = optionalUser.get(); // Получаем объект User

        // Получаем окружения пользователя
        List<Environment> environments = environmentService.getEnvironmentsByAuthorId(author.getId());

        // Формируем ответ
        return createResponse(author, environments);
    }

    private ResponseEntity<Object> createResponse(User author, List<Environment> environments) {
        // Создаем объект с необходимыми полями
        if (environments.isEmpty()) {
            return new ResponseEntity<>(createEmptyResponse(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createEnvironmentResponse(author, environments), HttpStatus.OK);
        }
    }

    private Environment createEmptyResponse(User author) {
        // Возвращаем пустой объект Environment с автором
        Environment emptyEnvironment = new Environment();
        emptyEnvironment.setId(null); // или 0
        emptyEnvironment.setAuthor(new User(author.getId(), author.getName())); // Устанавливаем автор
        emptyEnvironment.setName(""); // Пустое имя
        emptyEnvironment.setCreatedTime(LocalDateTime.now()); // Или какое-то значение по умолчанию
        emptyEnvironment.setIsPublic(false); // Или любое другое значение по умолчанию
        emptyEnvironment.setDirectories(List.of()); // Или пустой список, если директории отсутствуют
        return emptyEnvironment; 
    }
    
    private Environment createEnvironmentResponse(User author, List<Environment> environments) {
        // Возвращаем первую найденную среду с нужными полями
        Environment env = environments.get(0);
        Environment responseEnvironment = new Environment();
        responseEnvironment.setId(env.getId());
        responseEnvironment.setAuthor(new User(author.getId(), author.getName())); // Устанавливаем автор
        responseEnvironment.setName(env.getName());
        responseEnvironment.setCreatedTime(env.getCreatedTime());
        responseEnvironment.setIsPublic(env.getIsPublic());
        responseEnvironment.setDirectories(env.getDirectories()); // Предполагается, что у вас есть метод getDirectories() в классе Environment
        return responseEnvironment; 
    }
}
