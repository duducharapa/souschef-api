package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    /**
     * Searches all {@link Item Items} owned by a specific{@link User} using the email.
     *
     * @param ownerEmail The user email.
     * @return The list of items owned.
     */
    @Query("SELECT i FROM Item i WHERE i.user.email = :ownerEmail")
    List<Item> findAllByOwner(String ownerEmail);

    /**
     * Searches the quantity of an {@link Item} owned by an {@link User} using the {@link Product} ID.
     * <p>
     * If the application not founds any quantity, it'll throw an error.
     *
     * @param productId The product ID.
     * @param ownerEmail THe user email.
     * @return The quantity of item found.
     */
    @Query("SELECT i.quantity FROM Item i JOIN i.product p JOIN i.user u WHERE p.id = :productId AND u.email = :ownerEmail")
    long getQuantityByProductId(UUID productId, String ownerEmail);

}