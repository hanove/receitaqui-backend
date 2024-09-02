package com.api.receitaqui.dto;

import com.api.receitaqui.model.Role;

import java.util.List;

public record RecoveryUserDTO(Long id, String email, List<Role> roles) {
}
