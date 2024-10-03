package com.example.ametist.Service;
import com.example.ametist.models.Environment;
import com.example.ametist.EnvironmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class My_En_serv {
    private EnvironmentRepository environmentRepository = null;

    public My_En_serv(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    public List<Environment> getEnvironmentsByAuthorId(Integer authorId) {
        return environmentRepository.findByAuthorId(authorId);
    }
}
