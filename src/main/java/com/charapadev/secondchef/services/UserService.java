package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.dtos.ShowUserDTO;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

/**
 * Service used to manipulate the {@link User user} instances.
 * <p>
 * The manipulation of users here is different from manipulation related to authorization/authentication provided
 * on Spring Security.
 */

@Service
@Slf4j(topic = "User service")
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Checks if a {@link User user} is unique.
     * <p>
     * To be considered unique, a user MUST have exclusives ID and NAME.
     *
     * @param user The user to check.
     * @throws RuntimeException If the user already exists.
     */
    private void isUnique(User user) throws RuntimeException {
        boolean userAlreadyExists = userRepository.existsByEmail(user.getEmail());

        if (userAlreadyExists) {
            throw new RuntimeException("User already exists");
        }
    }

    /**
     * Registers a new {@link User user} on application.
     *
     * @param createDTO The creation information.
     * @return The created user.
     * @see CreateUserDTO Creation user schema.
     */
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

    /**
     * Searches and, if necessary, creates an {@link User user} using the given data.
     * <p>
     * First, the function check if the user exists by the given email on DTO provided.
     * Based on this, the user should be created before search the rest of information about the user.
     *
     * @param createDTO The creation information.
     * @return The user found or created.
     * @see CreateUserDTO Creation user schema.
     * @throws NoSuchElementException If none user was found when searched by email address.
     */
    public User findOrCreate(CreateUserDTO createDTO) throws NoSuchElementException {
        boolean userNotExists = !userRepository.existsByEmail(createDTO.email());

        if (userNotExists) {
            create(createDTO);
        }

        return userRepository.findByEmail(createDTO.email()).orElseThrow();
    }

    /**
     * Searches all the {@link User users} registered.
     *
     * @return The list of users.
     */
    public List<ShowUserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(this::convertToShow)
            .toList();
    }

    /**
     * Searches an unique {@link User user} using the given ID.
     *
     * @param userId The user ID.
     * @return The user found inside an Optional instance.
     *
     * @see Optional Optional specification.
     */
    public Optional<User> findOne(UUID userId) {
        return userRepository.findById(userId);
    }

    /**
     * Searches and returns a shorted instance of {@link User user} using the given ID.
     * <p>
     * This method has the same purpose of {@link #findOne(UUID) findOne method}, but clear the data of recipe before return.
     *
     * @param userId The user ID.
     * @return The shorted user.
     * @throws NoSuchElementException If none user is found.
     */
    public ShowUserDTO findOneToShow(UUID userId) throws NoSuchElementException {
        User user = findOne(userId).orElseThrow();

        return convertToShow(user);
    }

    /**
     * Searches and returns an {@link User user} using the given email address.
     * <p>
     * This method has the same purpose of {@link #findOne(UUID)}, but differs on criteria to search a user.
     * It's useful because the username of {@link Authentication Authentication} object is the unique email.
     * Then, it works as a shortcut to access the authenticated user.
     *
     * @param email The user email to search.
     * @return The user found.
     * @throws NoSuchElementException If no user was found.
     * @see Authentication Spring Security Authentication specification.
     */
    public User findByEmail(String email) throws NoSuchElementException {
        return userRepository.findByEmail(email)
            .orElseThrow();
    }

    /**
     * Converts a given {@link User user} to an {@link ShowUserDTO exposable DTO}.
     * <p>
     * This method is used to prevent external users to see private or unwanted data about a user.
     *
     * @param user The user to convert.
     * @return The converted user.
     */
    public ShowUserDTO convertToShow(User user) {
        return new ShowUserDTO(
            user.getId(),
            user.getEmail()
        );
    }

    /**
     * Removes an instance of {@link User user} from application using the given ID.
     *
     * @param userId The user ID.
     */
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

}
