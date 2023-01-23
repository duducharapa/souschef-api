package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(
        "SELECT case WHEN count(u) > 0 THEN true ELSE false END " +
        "FROM User u WHERE u.email = :userEmail"
    )
    boolean existsByEmail(String userEmail);

}
