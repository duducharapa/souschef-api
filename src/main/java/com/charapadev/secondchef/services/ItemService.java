package com.charapadev.secondchef.services;

import com.charapadev.secondchef.dtos.CreateItemDTO;
import com.charapadev.secondchef.dtos.ShowItemDTO;
import com.charapadev.secondchef.dtos.UpdateItemDTO;
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
    public List<ShowItemDTO> list(String ownerEmail) {
        return itemRepository.findAllByOwner(ownerEmail).stream()
            .map(this::convertToShow)
            .toList();
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

}
