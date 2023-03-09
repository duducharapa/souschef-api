package com.charapadev.secondchef.controllers;

import com.charapadev.secondchef.dtos.CreateItemDTO;
import com.charapadev.secondchef.dtos.ShowItemDTO;
import com.charapadev.secondchef.dtos.UpdateItemDTO;
import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.services.ItemService;
import com.charapadev.secondchef.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller used to manipulate anything about {@link Item item} instances.
 *<p>
 * To access any resource here, the user must be fully authenticated.
 * Also, the users access only your own items, except an admin.
 */

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    /**
     * Lists all {@link Item items} owned by user.
     *
     * @return The 200(OK) HTTP code with the list of items found.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/200">HTTP 200 code specification.</a>
     */
    @GetMapping
    public ResponseEntity<List<ShowItemDTO>> list(Authentication auth) {
        List<ShowItemDTO> items = itemService.list(auth.getName());

        return ResponseEntity.ok(items);
    }

    /**
     * Inserts a new {@link Item item} for a user.
     *
     * @param createDTO The initial data the item should have.
     * @return The 201(Created) HTTP code with the created item.
     *
     * @see CreateItemDTO Create item schema.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/201">HTTP 201 code specification.</a>
     */
    @PostMapping
    public ResponseEntity<ShowItemDTO> create(@RequestBody CreateItemDTO createDTO, Authentication auth) {
        User owner = userService.findByEmail(auth.getName());
        ShowItemDTO itemCreated = itemService.create(createDTO, owner);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCreated);
    }

    /**
     * Updates the quantity of an existent {@link Item item}.
     *
     * @param itemId The item identifier.
     * @param updateDTO The data to update on item.
     * @return The 204(No Content) HTTP informing the successful update.
     * @see UpdateItemDTO Update item schema.
     * @see <a href="https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Status/204">HTTP 204 code specification.</a>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") UUID itemId, @RequestBody UpdateItemDTO updateDTO) {
        itemService.update(itemId, updateDTO);

        return ResponseEntity.noContent().build();
    }

}
