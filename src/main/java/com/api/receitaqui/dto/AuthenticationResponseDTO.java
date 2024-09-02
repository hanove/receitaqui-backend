package com.api.receitaqui.dto;

import com.api.receitaqui.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDTO {
    private String token;
    private User user;

    public AuthenticationResponseDTO(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
