package com.manuelberganza.xclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.services.ILikeService;
import com.manuelberganza.xclone.services.IPostService;
import com.manuelberganza.xclone.services.IUserService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILikeService likeService;

    @GetMapping("/form")
    public String mostrarPostForm(Post post, Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);

        return "post-form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Post post, BindingResult result,  Model model, Authentication auth) {
        if (result.hasErrors()) {
            User user = userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
            
            return "post-form";
        }

        postService.save(post, auth.getName());
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String postById(@PathVariable("id") Integer id, Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        Post post = postService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("post", post);

        return "edit-post";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Integer id, Post post) {
        Post postDb = postService.findById(id);
        
        System.out.println(post.getContent());

        postDb.setContent(post.getContent());
        postService.updatePost(postDb);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Integer id) {
        Post post = postService.findById(id);
        postService.deletePost(post);

        return "redirect:/";
    }

    @PostMapping("/{id}/like")
    @ResponseBody
    public boolean toggleLike(@PathVariable Integer id, Authentication auth) {
        return likeService.toggleLikeForPost(id, auth.getName());
    }

}
