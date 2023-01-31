package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.dtos.ShowUserDTO;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j(topic = "User service")
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void isUnique(User user) throws RuntimeException {
        boolean userAlreadyExists = userRepository.existsByEmail(user.getEmail());

        if (userAlreadyExists) {
            throw new RuntimeException("User already exists");
        }
    }

    public ShowUserDTO create(CreateUserDTO createDTO) {
        String encodedPass = passwordEncoder.encode(createDTO.password());

        User userToCreate = User.builder()
            .email(createDTO.email())
            .password(encodedPass)
            .build();

        isUnique(userToCreate);

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

    public Optional<User> findOne(UUID userId) {
        return userRepository.findById(userId);
    }

    public ShowUserDTO findOneToShow(UUID userId) {
        User user = findOne(userId).orElseThrow();

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
