package com.manuelberganza.xclone.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.manuelberganza.xclone.models.Post;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.models.UserValidationGroups;
import com.manuelberganza.xclone.services.ILikeService;
import com.manuelberganza.xclone.services.IPostService;
import com.manuelberganza.xclone.services.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPostService postService;

    @Autowired
    private ILikeService likeService;

    @GetMapping()
    public String index(Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        List<Post> posts = postService.findAll();
        Map<Integer, Boolean> likedPosts = new HashMap<>();

        for (Post post : posts) {
            boolean liked = likeService.isPostLikedByUser(post, auth.getName());
            likedPosts.put(post.getId(), liked);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("likedPosts", likedPosts);

        return "index";
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam( value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Usuario o Contrasena incorrectos, vuelvelo a intentar");
        }

        return "login"; 
    }

    @PostMapping("/login")
    public String loginForm() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String mostrarRegister(User user) {

        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@Validated(UserValidationGroups.Registration.class) User user, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            System.out.println(result.getFieldErrors());
            return "register";
        }
        
        attr.addFlashAttribute("msg", "Usuario registrado con exito!");
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        List<Post> posts = postService.findAllByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "profile";
    }

}
