package com.manuelberganza.xclone.services;

import java.util.List;

import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;

public interface IPostService {
    Post save(Post post, String username);
    Post updatePost(Post post);
    Post findById(Integer id);
    List<Post> findAll();
    List<Post> findAllByUser(User user);
    void deletePost(Post post);
}
