package com.example.ametist.Controllers;

import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Environment;
import com.example.ametist.models.User;
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

    public EnvironmentController(My_En_serv environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<Object> getEnvironmentByUsername(@PathVariable String username) {
        Optional<User> optionalUser = environmentService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND); 
        }

        User author = optionalUser.get(); 

        List<Environment> environments = environmentService.getEnvironmentsByAuthorId(author.getId());

        return createResponse(author, environments);
    }

    private ResponseEntity<Object> createResponse(User author, List<Environment> environments) {
        if (environments.isEmpty()) {
            return new ResponseEntity<>(createEmptyResponse(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createEnvironmentResponse(author, environments), HttpStatus.OK);
        }
    }

    private Environment createEmptyResponse(User author) {
        Environment emptyEnvironment = new Environment();
        emptyEnvironment.setId(null); 
        emptyEnvironment.setAuthor(new User(author.getId(), author.getName())); 
        emptyEnvironment.setName(""); 
        emptyEnvironment.setCreatedTime(LocalDateTime.now()); 
        emptyEnvironment.setIsPublic(false); 
        emptyEnvironment.setDirectories(List.of()); 
        return emptyEnvironment; 
    }
    
    private Environment createEnvironmentResponse(User author, List<Environment> environments) {
        Environment env = environments.get(0);
        Environment responseEnvironment = new Environment();
        responseEnvironment.setId(env.getId());
        responseEnvironment.setAuthor(new User(author.getId(), author.getName())); 
        responseEnvironment.setName(env.getName());
        responseEnvironment.setCreatedTime(env.getCreatedTime());
        responseEnvironment.setIsPublic(env.getIsPublic());
        responseEnvironment.setDirectories(env.getDirectories()); 
        return responseEnvironment; 
    }
}
