package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j(topic = "User service")
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(CreateUserDTO createDTO) {
        User userToCreate = User.builder()
            .email(createDTO.email())
            .password(createDTO.password())
            .build();

        userToCreate = userRepository.save(userToCreate);
        log.info("Created an user: {}", userToCreate);

        return userToCreate;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow();
    }

    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

}
