package com.manuelberganza.xclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}
