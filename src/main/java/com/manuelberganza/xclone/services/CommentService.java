package com.manuelberganza.xclone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuelberganza.xclone.models.Comment;
import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.repositories.CommentRepository;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Override
    public List<Comment> findByPost(Post post) {
        return commentRepo.findByPost(post);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepo.delete(comment);
    }

    @Override
    public Comment findById(Integer id) {
        Optional<Comment> opt = commentRepo.findById(id);
        
        if (opt.isPresent()) {
            return opt.get();
        }

        return null;
    }

    
}
