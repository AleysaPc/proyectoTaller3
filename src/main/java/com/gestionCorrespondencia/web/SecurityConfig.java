package com.gestionCorrespondencia.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User
                .withUsername("user")
                .password("{noop}123")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}1234")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    //Autorizacion para el manejo de Urls
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((solicitud) -> solicitud
                .requestMatchers("/editar/**", "/agregar/**", "/eliminar/**").hasAnyRole("ADMIN")
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin((login) -> login
                .loginPage("/login")
                .permitAll() // Permitir acceso a la página de inicio de sesión para todos los usuarios
                )
                .exceptionHandling((exception) -> exception
                .accessDeniedPage("/errores/403")
                );
        return http.build();
    }
}
