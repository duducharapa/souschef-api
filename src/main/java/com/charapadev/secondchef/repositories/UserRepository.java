package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
