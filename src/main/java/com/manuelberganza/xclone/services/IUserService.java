package com.manuelberganza.xclone.services;

import com.manuelberganza.xclone.models.User;

public interface IUserService {
    User save(User user);
    User updateUser(User user);
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}


