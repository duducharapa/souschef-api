package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.dtos.ShowUserDTO;
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
    public ResponseEntity<List<ShowUserDTO>> list() {
        List<ShowUserDTO> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<ShowUserDTO> create(@RequestBody @Valid CreateUserDTO createDTO) {
        ShowUserDTO createdUser = userService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowUserDTO> findOne(@PathVariable("id") UUID userId) {
        ShowUserDTO userFound = userService.findOneToShow(userId);

        return ResponseEntity.ok(userFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID userId) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

}
