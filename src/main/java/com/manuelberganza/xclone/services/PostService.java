package com.manuelberganza.xclone.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.repositories.PostRepository;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired IUserService userService;

    @Override
    public Post save(Post post, String username) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeNow = now.format(formatter); 
        Timestamp timestamp = Timestamp.valueOf(dateTimeNow);

        User user = userService.findByUsername(username);

        post.setCreatedAt(timestamp);
        post.setLastUpdate(timestamp);
        post.setUser(user);

        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeNow = now.format(formatter); 
        Timestamp timestamp = Timestamp.valueOf(dateTimeNow);
        post.setLastUpdate(timestamp);
        
        return postRepo.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepo.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Post findById(Integer id) {
        Optional<Post> opt = postRepo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }

        return null;
    }

    @Override
    public List<Post> findAllByUser(User user) {
        return postRepo.findByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public void deletePost(Post post) {
        postRepo.delete(post);
    }

}
