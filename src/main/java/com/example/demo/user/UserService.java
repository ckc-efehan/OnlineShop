package com.example.demo.user;

import com.example.demo.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) throws Exception {

        if (userRepository.findByUserName(user.getUserName()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User existiert bereits.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
