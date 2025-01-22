package com.api.receitaqui.controller;

import com.api.receitaqui.dto.AuthenticationResponseDTO;
import com.api.receitaqui.dto.CreateUserDTO;
import com.api.receitaqui.dto.LoginUserDTO;
import com.api.receitaqui.dto.RecoveryJwtTokenDTO;
import com.api.receitaqui.model.Receita;
import com.api.receitaqui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/savedRecipes")
    public ResponseEntity<List<Receita>> getSavedRecipes() {
        List<Receita> savedRecipes = userService.getSavedRecipesOfLoggedUser();
        return new ResponseEntity<>(savedRecipes, HttpStatus.OK);
    }

    @PostMapping("/saveRecipe/{id}")
    public ResponseEntity<Void> saveRecipe(@PathVariable Long id) {
        userService.saveRecipe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/removeSavedRecipe/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        userService.removeSavedRecipe(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
