package com.api.receitaqui.controller;

import com.api.receitaqui.dto.AuthenticationResponseDTO;
import com.api.receitaqui.dto.CreateUserDTO;
import com.api.receitaqui.dto.LoginUserDTO;
import com.api.receitaqui.dto.RecoveryJwtTokenDTO;
import com.api.receitaqui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDto) {
        AuthenticationResponseDTO response = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
