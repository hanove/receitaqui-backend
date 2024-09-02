package com.api.receitaqui.dto;

import com.api.receitaqui.model.RoleName;

public record CreateUserDTO(String name, String email, String password, RoleName role) {
}
