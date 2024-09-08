package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Verwende einen sicher generierten Schlüssel
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Extrahiere den Benutzernamen aus dem JWT-Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrahiere das Ablaufdatum aus dem JWT-Token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generische Methode zum Extrahieren von Claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrahiere alle Claims aus dem Token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Verwende den neuen Key-Typ
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Überprüfe, ob das Token abgelaufen ist
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generiere ein JWT-Token für einen Benutzer
    public String generateToken(String username) {
        Map<String, Object> claims = Map.of();  // Leere Claims (du kannst hier zusätzliche Daten hinzufügen)
        return createToken(claims, username);
    }

    // Erstelle das JWT-Token mit den Claims und dem Betreff (Benutzername)
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 Stunden gültig
                .signWith(SECRET_KEY) // Neuer Key-Typ
                .compact();
    }

    // Validierung des JWT-Tokens anhand des Benutzernamens und der Token-Gültigkeit
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
