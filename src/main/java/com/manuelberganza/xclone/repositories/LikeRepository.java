package com.manuelberganza.xclone.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manuelberganza.xclone.models.Like;
import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);
}