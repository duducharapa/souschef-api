package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    /**
     * Searches all {@link Item Items} owned by a specific {@link User} using the email.
     *
     * @param ownerEmail The user email.
     * @return The list of items owned.
     */
    @Query("SELECT i FROM Item i WHERE i.user.email = :ownerEmail")
    List<Item> findAllByOwner(String ownerEmail);

    /**
     * Searches an {@link Item} owned by an {@link User} using the {@link Product} ID.
     *
     * @param productId The product ID.
     * @param ownerEmail The user email.
     * @return The item found, if exists.
     */
    @Query("SELECT i FROM Item i JOIN i.product p WHERE p.id = :productId AND i.user.email = :ownerEmail")
    Optional<Item> findByProductIdAndOwner(UUID productId, String ownerEmail);

}