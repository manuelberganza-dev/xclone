package com.manuelberganza.xclone.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manuelberganza.xclone.models.Comment;
import com.manuelberganza.xclone.models.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
