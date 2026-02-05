package com.security.cracking.controller;

import com.security.cracking.dto.UserRequestDTO;
import com.security.cracking.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO userReq){

        return null;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUser(){
        return null;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getAllUser(){
        return null;
    }

    //TODO: Replantear DTO
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(UserRequestDTO userReq){
        return null;
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(){
        return null;
    }
}
