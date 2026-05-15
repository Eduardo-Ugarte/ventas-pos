package com.pos.ventas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Headers de seguridad HTTP
            .headers(headers -> headers
                .frameOptions(frame -> frame.deny())
                .contentTypeOptions(ct -> {})
            )

            // Control de acceso por roles
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")
                .requestMatchers("/productos", "/productos/**",
                                 "/ventas",   "/ventas/**").hasAnyRole("ADMIN", "CAJERO")
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )

            // Login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // Protección CSRF activa para formularios web
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/productos/**", "/ventas/**")
            );

        return http.build();
    }
}