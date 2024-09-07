package com.example.demo.user;

import com.example.demo.exceptions.UserAlreadyExistsException;
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

    public UserEntity save(UserEntity user) throws Exception {

        if (userRepository.findByUserName(user.getUserName()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User existiert bereits.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Authentifizierungsmethode
    public boolean authenticate(String username, String password) {
        // Finde den Benutzer nach Benutzername
        Optional<UserEntity> optionalUser = userRepository.findByUserName(username);

        // Überprüfe, ob der Benutzer existiert und das Passwort korrekt ist
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            // Passwortvergleich: Verschlüsseltes Passwort gegen das eingegebene
            return passwordEncoder.matches(password, user.getPassword());
        }

        // Rückgabe "false", wenn der Benutzer nicht existiert oder das Passwort falsch ist
        return false;
    }
}
