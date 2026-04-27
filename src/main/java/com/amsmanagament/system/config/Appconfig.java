package com.amsmanagament.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class Appconfig {

    private final JwtValidator jwtValidator;

    public Appconfig(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/signin","/send/sendotp","/send/forget-password/**","/catagories/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/farmer/**").hasAnyRole("ADMIN","FARM")
                        .requestMatchers("/api/buyer/**").hasAnyRole("ADMIN","BUYER")
                        .requestMatchers("/api/farmerbuyer/**").hasAnyRole("FARM","BUYER")
                        .requestMatchers("/api/notification/**").hasAnyRole("FARM","BUYER","ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
            config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
            config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));

            config.setAllowedHeaders(Arrays.asList("*"));


            config.setAllowCredentials(true);
            return config;
        };
    }
}
