package com.manuelberganza.xclone.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manuelberganza.xclone.models.Comment;
import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.services.ICommentService;
import com.manuelberganza.xclone.services.IPostService;
import com.manuelberganza.xclone.services.IUserService;


@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;


    @PostMapping("/{idPost}")
    public String commentForm(@PathVariable("idPost") Integer idPost, @RequestParam("content") String content, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        Post post = postService.findById(idPost);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeNow = now.format(formatter); 
        Timestamp timestamp = Timestamp.valueOf(dateTimeNow);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(timestamp);
        comment.setUser(user);
        comment.setPost(post);

        commentService.save(comment);

        return "redirect:/";
    }

    @GetMapping("/{idPost}")
    public String commentsPost(@PathVariable("idPost") Integer id, Model model, Authentication auth) {
        Post post = postService.findById(id);
        List<Comment> comments = commentService.findByPost(post);
        User user = userService.findByUsername(auth.getName());

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("user", user);

        return "comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id) {
        Comment comment = commentService.findById(id);
        commentService.delete(comment);

        return "redirect:/";
    }
}
