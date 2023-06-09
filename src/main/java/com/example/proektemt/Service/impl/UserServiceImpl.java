package com.example.proektemt.Service.impl;

import com.example.proektemt.Model.Exceptions.UserAlreadyExistException;
import com.example.proektemt.Model.Exceptions.UserIsNotFoundException;
import com.example.proektemt.Model.User;
import com.example.proektemt.Repository.UserRepository;
import com.example.proektemt.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(()-> new UserIsNotFoundException(userId));
    }

    @Override
    public User registerUser(User user) {
        if(this.userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        return this.userRepository.findById(s)
                .orElseThrow(()-> new UsernameNotFoundException(s));
    }

}
