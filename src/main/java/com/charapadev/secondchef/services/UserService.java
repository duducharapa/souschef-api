package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.dtos.ShowUserDTO;
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

    public ShowUserDTO create(CreateUserDTO createDTO) {
        User userToCreate = User.builder()
            .email(createDTO.email())
            .password(createDTO.password())
            .build();

        userToCreate = userRepository.save(userToCreate);
        log.info("Created an user: {}", userToCreate);

        return convertToShow(userToCreate);
    }

    public List<ShowUserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(this::convertToShow)
            .toList();
    }

    public User findOne(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow();
    }

    public ShowUserDTO findOneToShow(UUID userId) {
        User user = findOne(userId);

        return convertToShow(user);
    }

    public ShowUserDTO convertToShow(User user) {
        return new ShowUserDTO(
            user.getId(),
            user.getEmail()
        );
    }

    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

}
