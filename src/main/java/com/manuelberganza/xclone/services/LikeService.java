package com.manuelberganza.xclone.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuelberganza.xclone.models.Like;
import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.repositories.LikeRepository;
import com.manuelberganza.xclone.repositories.PostRepository;
import com.manuelberganza.xclone.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LikeService implements ILikeService {

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean toggleLikeForPost(Integer id, String username) {
        Optional<User> user = userRepo.findByUsername(username);
        Post post = postRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));

        Optional<Like> likeOpt = likeRepo.findByUserAndPost(user.get(), post);

        if (likeOpt.isPresent()) {
            likeRepo.delete(likeOpt.get());
            return false; // Se eliminó el "me gusta"
        } else {
            Like like = new Like();
            like.setUser(user.get());
            like.setPost(post);
            likeRepo.save(like);
            return true; // Se añadió el "me gusta"
        }
    }

    @Override
    public boolean isPostLikedByUser(Post post, String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return likeRepo.existsByUserAndPost(user.get(), post);
    }

}
