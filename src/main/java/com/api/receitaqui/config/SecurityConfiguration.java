package com.api.receitaqui.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login", // Url que usaremos para fazer login
            "/users", // Url que usaremos para criar um usuário
            //"/receita",
//            "/receita/category/Salgado", // Url que usaremos para buscar receitas por categoria
//            "/receita/category/Doce", // Url que usaremos para buscar receitas por categoria
//            "/receita/category/Bebida", // Url que usaremos para buscar receitas por categoria
//            "/receita/category/Outros", // Url que usaremos para buscar receitas por categoria
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/receita/**",
            "/receita/name/**", // Url que usaremos para buscar uma receita por id
            "/receita/category/**" // Url que usaremos para criar uma receita
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String[] ENDPOINTS_CUSTOMER = {
            "/users/test/customer"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String[] ENDPOINTS_ADMIN = {
            "/users/test/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session creation policy as stateless
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/receita/category/**").permitAll()
                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                                .anyRequest().denyAll())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
