package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT i FROM Item i WHERE i.user.email = :ownerEmail")
    List<Item> findAllByOwner(String ownerEmail);

    @Query("SELECT i.quantity FROM Item i JOIN i.product p JOIN i.user u WHERE p.id = :productId AND u.email = :userEmail")
    long getQuantityByProductId(UUID productId, String userEmail);

}