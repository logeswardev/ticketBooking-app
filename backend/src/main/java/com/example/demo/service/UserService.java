package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> createUserFromRequest(UserRequest userRequest) {
        User user = new User();
        user.setEmailId(userRequest.getEmailId());
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
        try{
            String userEmailAddress = userRequest.getEmailId();
            User user = userRepository.findByEmail(userEmailAddress);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(userEmailAddress.equalsIgnoreCase(user.getEmailId()) &&
                    passwordEncoder.matches(userRequest.getPassword(), user.getPassword())){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        }catch (NotFoundException e){
            throw new NotFoundException("User not Found");
        }
    }
}
