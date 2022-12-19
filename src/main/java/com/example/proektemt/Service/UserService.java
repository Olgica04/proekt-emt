package com.example.proektemt.Service;


import com.example.proektemt.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User findById(String userId);

    User registerUser(User user);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
