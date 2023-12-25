package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> createUserFromRequest(UserRequest userRequest) {
        User user = new User();
        user.setEmailAddress(userRequest.getEmailId());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        if (Objects.equals(userRequest.getPassword(), userRequest.getRetryPassword())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<User> userLogin(UserRequest userRequest) {
        String userEmailAddress = userRequest.getEmailId();
        Optional<User> user = userRepository.findByEmailAddress(userRequest.getEmailId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(user.isPresent() && userEmailAddress.equalsIgnoreCase(user.get().getEmailAddress()) &&
                passwordEncoder.matches(userRequest.getPassword(), user.get().getPassword())){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
