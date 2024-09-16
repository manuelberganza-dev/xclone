package com.manuelberganza.xclone.services;

import java.util.List;

import com.manuelberganza.xclone.models.Comment;
import com.manuelberganza.xclone.models.Post;

public interface ICommentService {
    List<Comment> findByPost(Post post);
    Comment save(Comment comment);
    Comment findById(Integer id);
    void delete(Comment comment);
}
