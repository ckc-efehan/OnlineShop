package com.example.demo.config;

import com.example.demo.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/users/login", "/api/users/register").permitAll()  // Erlaubt Login und Registrierung ohne Token
                        .anyRequest().authenticated()  // Alle anderen Anfragen benötigen Authentifizierung
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT-Filter vor dem Standard-Auth-Filter
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // CORS-Konfiguration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));  // Erlaubte Origins (Frontend-URL)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Erlaubte HTTP-Methoden
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));  // Erlaubte Header
        configuration.setAllowCredentials(true);  // Falls Cookies/Authentication benötigt werden

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // CORS für alle Pfade konfigurieren
        return source;
    }
}
