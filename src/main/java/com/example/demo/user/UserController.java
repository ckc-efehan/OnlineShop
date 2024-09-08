package com.example.demo.user;

import com.example.demo.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@CrossOrigin(origins = { "http://localhost:5173" })
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        try {
            String token = userService.saveAndGenerateToken(user);
            return ResponseEntity.ok(token);  // Gibt das JWT-Token zurück
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein unerwarteter Fehler ist aufgetreten.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user) {
        try {
            String token = userService.authenticateAndGenerateToken(user.getUserName(), user.getPassword());
            if (token != null) {
                // Setze das Token in einem HttpOnly-Cookie
                ResponseCookie cookie = ResponseCookie.from("jwtToken", token)
                        .httpOnly(true)   // Schützt den Cookie vor JavaScript-Zugriff
                        .secure(true)     // Verwende dies nur, wenn HTTPS aktiviert ist
                        .path("/")        // Der Cookie gilt für den gesamten Pfad
                        .maxAge(Duration.ofHours(10))  // Gültigkeit des Cookies
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body("Login erfolgreich.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Benutzername oder Passwort falsch.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten.");
        }
    }

}
