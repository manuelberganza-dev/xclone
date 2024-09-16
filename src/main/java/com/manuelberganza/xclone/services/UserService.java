package com.manuelberganza.xclone.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manuelberganza.xclone.models.Role;
import com.manuelberganza.xclone.models.User;
import com.manuelberganza.xclone.repositories.RoleRepository;
import com.manuelberganza.xclone.repositories.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public User save(User user) { 
        List<Role> roles = new LinkedList<>();
        Optional<Role> opt = roleRepo.findById(1);
        if (opt.isPresent()) {
            roles.add(opt.get());
        }

        user.setRoles(roles);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> opt = userRepo.findByUsername(username);
        if (opt.isPresent()) {
            return opt.get();
        }

        return null;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

}
