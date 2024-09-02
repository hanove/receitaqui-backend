package com.api.receitaqui.dto;

import java.util.List;

public record ReceitaDTO(String nome, List<String> ingredientes, String modoPreparo, String tempoPreparo, String rendimento,
                         String categoria, String dificuldade, String imagem) {
}
