package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
