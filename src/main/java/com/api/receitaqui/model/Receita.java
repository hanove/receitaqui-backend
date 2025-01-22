package com.api.receitaqui.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ElementCollection
    private List<String> ingredientes;
    private String modoPreparo;
    private String tempoPreparo;
    private String rendimento;
    private String categoria;
    private String dificuldade;
    private String imagem;
}
