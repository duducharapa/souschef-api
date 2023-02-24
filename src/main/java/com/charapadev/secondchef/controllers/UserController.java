package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateUserDTO;
import com.charapadev.secondchef.dtos.ShowUserDTO;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Controller used to manipulate anything about {@link User user} instances.
 * <p>
 * To access any resource here, the user must be fully authenticated,
 * <b>EXCEPT</b> the endpoint to <b>REGISTER</b> new users.
 */

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Lists all {@link User users} registered on application.
     *
     * @return The 200(OK) HTTP code with the list of users found.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping
    public ResponseEntity<List<ShowUserDTO>> list() {
        List<ShowUserDTO> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    /**
     * Registers a new {@link User user} on application.
     *
     * @param createDTO The initial data the user should provide.
     * @return The 201(Created) HTTP code with the created user.
     *
     * @see CreateUserDTO Create user schema.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/201">HTTP 201 code specification.</a>
     */
    @PostMapping
    public ResponseEntity<ShowUserDTO> create(@RequestBody @Valid CreateUserDTO createDTO) {
        ShowUserDTO createdUser = userService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Searches a unique {@link User user} using the given identifier.
     *
     * @param userId The user ID.
     * @return The 200(OK) HTTP code with the user found.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShowUserDTO> findOne(@PathVariable("id") UUID userId) {
        ShowUserDTO userFound = userService.findOneToShow(userId);

        return ResponseEntity.ok(userFound);
    }

    /**
     * Removes an instance of {@link User user} using the given identifier.
     *
     * @param userId The user ID.
     * @return The 204(No Content) HTTP code.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/204">HTTP 204 code specification.</a>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID userId) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

}
