package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> fetchAllEmployees() {
        int count = 1;
        if(count!= 3) {
            userRepository.findAll();
        }
        return ResponseEntity.ok().build();
    }
}
