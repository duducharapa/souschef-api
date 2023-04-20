package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateItemDTO;
import com.charapadev.secondchef.dtos.ShowItemDTO;
import com.charapadev.secondchef.dtos.UpdateItemDTO;
import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.User;
import com.charapadev.secondchef.repositories.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Service used to manipulate the {@link Item items instances}.
 */

@Service
@Slf4j(topic = "Item service")
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductService productService;

    /**
     * Lists the {@link Item items} owned by a given user.
     *
     * @param ownerEmail The owner email.
     * @return The list of items owned.
     */
    public List<ShowItemDTO> listToShow(String ownerEmail) {
        List<Item> itemsOwned = list(ownerEmail);

        return itemsOwned.stream()
            .map(this::convertToShow)
            .toList();
    }

    public List<Item> list(String ownerEmail) {
        return itemRepository.findAllByOwner(ownerEmail);
    }

    /**
     * Create an {@link Item item} and link to an existent {@link Product product}.
     *
     * @param createDTO The creation information.
     * @param owner The owner for the item.
     * @return The item created.
     * @throws NoSuchElementException If no one product was found.
     */
    public ShowItemDTO create(CreateItemDTO createDTO, User owner) throws NoSuchElementException {
        Product productRelated = productService.findOne(createDTO.productId())
            .orElseThrow();
        boolean productAlreadyLinked = itemRepository.findByProductIdAndOwner(createDTO.productId(), owner.getEmail())
            .isPresent();

        if (productAlreadyLinked) {
            throw new RuntimeException("Já existe um item vinculado a esse produto");
        }

        Item itemToCreate = Item.builder()
            .quantity(createDTO.quantity())
            .user(owner)
            .product(productRelated)
            .build();

        itemToCreate = itemRepository.save(itemToCreate);
        log.info("Created an item: {}", itemToCreate);

        return convertToShow(itemToCreate);
    }

    /**
     * Updates the information of an existent {@link Item item}.
     * <p>
     * The unique information to update actually is the quantity of item.
     *
     * @param itemId The item identifier.
     * @param updateDTO The update information.
     * @throws NoSuchElementException If no item was found.
     */
    public void update(UUID itemId, UpdateItemDTO updateDTO) throws NoSuchElementException {
        Item itemFound = itemRepository.findById(itemId)
            .orElseThrow();

        itemFound.setQuantity(updateDTO.quantity());
        itemRepository.save(itemFound);
    }

    /**
     * Converts a given {@link Item item} to an {@link ShowItemDTO exposable DTO}.
     * <p>
     * This conversion removes the overload of attributes related to link between
     * {@link User users} and {@link Product products}.
     *
     * @param item The item to convert.
     * @return The converted item.
     */
    private ShowItemDTO convertToShow(Item item) {
        return new ShowItemDTO(
            item.getId(),
            item.getProduct().getName(),
            item.getQuantity()
        );
    }

    /**
     * Checks if a specific {@link User user} has some {@link Item item} enough to a given {@link Ingredient ingredient}.
     * <p>
     * To be considered enough, the item must have the quantity equal or greater than ingredient's quantity.
     * If cannot found an item linked to the product ID given, automatically will be returned a FALSE value.
     *
     * @param ingredientQuantity The ingredient quantity.
     * @param productId The product ID.
     * @param userEmail The user email address.
     * @return If the quantity of the item found is enough.
     */
    public boolean hasQuantityEnough(long ingredientQuantity, UUID productId, String userEmail) {
        try {
            long itemQuantity = itemRepository.findByProductIdAndOwner(productId, userEmail)
                .orElseThrow().getQuantity();

            return itemQuantity >= ingredientQuantity;
        } catch (Exception ex) {
            return false;
        }
    }

}
