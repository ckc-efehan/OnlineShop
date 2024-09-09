package com.example.demo.user;

import com.example.demo.jwt.JwtTokenUtil;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public String saveAndGenerateToken(UserEntity user) throws Exception {
        if (userRepository.findByUserName(user.getUserName()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Benutzernamen oder Email ist bereits vergeben.");
        }

        // Passwort verschlüsseln und Benutzer speichern
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        emailService.sendRegistrationEmail(user.getEmail(), user.getUserName());

        // JWT-Token für den Benutzer generieren und zurückgeben
        return jwtTokenUtil.generateToken(user.getUserName());
    }


    // Authentifizierungsmethode
    public String authenticateAndGenerateToken(String username, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByUserName(username);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Erzeuge und gebe das JWT-Token zurück
                return jwtTokenUtil.generateToken(user.getUserName());
            }
        }
        return null;
    }
}
