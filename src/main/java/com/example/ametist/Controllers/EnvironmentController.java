package com.example.ametist.Controllers;

import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Environment;
import com.example.ametist.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Environment getEnvironmentsByUsername(@PathVariable String username) {
        // Fetch user to ensure they exist
        Optional<User> optionalUser = environmentService.findUserByUsername(username);
        
        // If user is not found, handle accordingly (e.g., return a 404)
        if (optionalUser.isEmpty()) {
            // Handle user not found scenario
            return new Environment(); // or throw an exception
        }
        
        User author = optionalUser.get(); // Retrieve the User object

        List<Environment> environments = environmentService.getEnvironmentsByAuthorId(author.getId());

        // If no environments are found, return an empty Environment object
        if (environments.isEmpty()) {
            Environment emptyEnvironment = new Environment();
            emptyEnvironment.setId(null); // or 0
            emptyEnvironment.setName("");
            emptyEnvironment.setAuthor(author); // Set the author context
            return emptyEnvironment; // Return empty environment
        }

        return environments.get(0); // Return the first environment found
    }
}
