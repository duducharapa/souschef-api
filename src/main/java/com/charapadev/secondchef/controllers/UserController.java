package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserDTO createDTO) {
        User createdUser = userService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") UUID userId) {
        User userFound = userService.findOne(userId);

        return ResponseEntity.ok(userFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") UUID userId) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

}
