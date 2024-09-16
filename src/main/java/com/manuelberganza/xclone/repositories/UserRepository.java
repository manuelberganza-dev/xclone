package com.manuelberganza.xclone.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manuelberganza.xclone.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
