package com.api.receitaqui.service;

import com.api.receitaqui.dto.ReceitaDTO;
import com.api.receitaqui.exception.NotFoundException;
import com.api.receitaqui.model.Receita;
import com.api.receitaqui.repository.ReceitaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public Receita create(ReceitaDTO dto) {
        var receita = new Receita();
        BeanUtils.copyProperties(dto, receita);

        return receitaRepository.save(receita);
    }

    public Page<Receita> getAll(Pageable pageable) {
        return receitaRepository.findAll(pageable);
    }

    public Page<Receita> getByCategory(String categoria, Pageable pageable) {
        return receitaRepository.findByCategoria(categoria, pageable);
    }

    public Page<Receita> getByName(String nome, Pageable pageable) {
        return receitaRepository.findByNomeContaining(nome, pageable);
    }

    public Optional<Receita> getById(long id) {
        return receitaRepository.findById(id);
    }

    public Receita update(long id, ReceitaDTO dto) throws NotFoundException{
        var res = receitaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Receita " + id + " não existe.");
        }

        var receita = res.get();
        receita.setNome(dto.nome());
        receita.setIngredientes(dto.ingredientes());
        receita.setModoPreparo(dto.modoPreparo());
        receita.setTempoPreparo(dto.tempoPreparo());
        receita.setRendimento(dto.rendimento());
        receita.setCategoria(dto.categoria());
        receita.setDificuldade(dto.dificuldade());
        receita.setImagem(dto.imagem());

        return receitaRepository.save(receita);
    }

    public void delete(long id) throws NotFoundException {
        var res = receitaRepository.findById(id);

        if (res.isEmpty()) {
            throw new NotFoundException("Receita " + id + " não existe.");
        }

        receitaRepository.delete(res.get());
    }
}
