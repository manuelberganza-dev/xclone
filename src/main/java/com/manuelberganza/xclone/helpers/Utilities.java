package com.manuelberganza.xclone.helpers;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class Utilities {

    public static String savePhoto(MultipartFile multipart, String path, String username) {
        String originalName = multipart.getOriginalFilename();
        originalName.replace(" ", "-");
        String newName = username + "-" + originalName;


        try {
            File imagFile = new File(path + newName);
            multipart.transferTo(imagFile);
            return newName;
        } catch (Exception e) {
            return null;
        }
    } 
}
