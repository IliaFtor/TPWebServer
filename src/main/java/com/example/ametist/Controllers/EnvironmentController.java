package com.example.ametist.Controllers;

import com.example.ametist.Service.My_En_serv;
import com.example.ametist.models.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentController {

    private final My_En_serv environmentService;

    @Autowired
    public EnvironmentController(My_En_serv environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/by-author/{authorId}")
    public List<Environment> getEnvironmentsByAuthor(@PathVariable Integer authorId) {
        return environmentService.getEnvironmentsByAuthorId(authorId);
    }
}
