package com.example.proektemt.Service.impl;


import com.example.proektemt.Model.Exceptions.PasswordDoesntMatchException;
import com.example.proektemt.Model.Role;
import com.example.proektemt.Model.User;
import com.example.proektemt.Repository.RoleRepository;
import com.example.proektemt.Repository.UserRepository;
import com.example.proektemt.Service.AuthService;
import com.example.proektemt.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getCurrentUserId(){
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username, String password, String repeatedPassword){
        if(!password.equals(repeatedPassword)){
            throw new PasswordDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
       user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }

    @PostConstruct
    public void init(){
        if(!this.userRepository.existsById("admin")){
            User admin= new User();
            admin.setUsername("admin");
            admin.setPassword(this.passwordEncoder.encode("admin"));
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
    }
}
