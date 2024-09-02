package com.api.receitaqui.service;

import com.api.receitaqui.config.SecurityConfiguration;
import com.api.receitaqui.dto.AuthenticationResponseDTO;
import com.api.receitaqui.dto.CreateUserDTO;
import com.api.receitaqui.dto.LoginUserDTO;
import com.api.receitaqui.dto.RecoveryJwtTokenDTO;
import com.api.receitaqui.model.Role;
import com.api.receitaqui.model.UserDetailsImpl;
import com.api.receitaqui.repository.UserRepository;
import com.api.receitaqui.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public AuthenticationResponseDTO authenticateUser(LoginUserDTO loginUserDto) {
        try {
            // Cria um objeto de autenticação com o email e a senha do usuário
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

            // Autentica o usuário com as credenciais fornecidas
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // Obtém o objeto UserDetails do usuário autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            User user = getUserByEmail(userDetails.getUsername());

            // Gera um token JWT para o usuário autenticado
            return new AuthenticationResponseDTO(jwtTokenService.generateToken(userDetails), user);
            //return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
        }
        catch (Exception e) {
            throw new RuntimeException("Falha ao autenticar o usuário.", e);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public User getCurrentlyAuthenticatedUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUserByEmail(userDetails.getUsername());
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDTO createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}
