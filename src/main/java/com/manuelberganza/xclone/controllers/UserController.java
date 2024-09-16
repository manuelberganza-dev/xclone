package com.manuelberganza.xclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manuelberganza.xclone.helpers.Utilities;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.models.UserValidationGroups;
import com.manuelberganza.xclone.services.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/edit")
    public String updateProfileForm(User user, Model model, Authentication auth) {
        User usr = userService.findByUsername(auth.getName());
        model.addAttribute("user", usr);

        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@Validated(UserValidationGroups.UpdateProfile.class) User user, BindingResult result, @RequestParam("archivePhoto") MultipartFile multipart, Authentication auth) {
        User usrDb = userService.findByUsername(auth.getName());

        if (result.hasErrors()) {
            System.out.println(result.getFieldErrors());
            return "edit-profile";
        }

        if (!multipart.isEmpty()) {
            String path = "/xclone/img-profiles/";
            String imgName = Utilities.savePhoto(multipart, path, auth.getName());

            if (imgName != null) {

                usrDb.setPhoto(imgName);
            }
        }

        usrDb.setName(user.getName());
        usrDb.setLastname(user.getLastname());
        userService.updateUser(usrDb);

        return "redirect:/";
    }

}
