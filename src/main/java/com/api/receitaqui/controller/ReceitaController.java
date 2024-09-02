package com.api.receitaqui.controller;

import com.api.receitaqui.dto.ReceitaDTO;
import com.api.receitaqui.model.Receita;
import com.api.receitaqui.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ReceitaDTO dto) {
        try {
            var res = receitaService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<Receita>> getAll(Pageable pageable) {
        return ResponseEntity.ok(receitaService.getAll(pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Receita>> getByCategory(@PathVariable String category, Pageable pageable) {
        return ResponseEntity.ok(receitaService.getByCategory(category, pageable));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<Receita>> getByName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(receitaService.getByName(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> getById(@PathVariable long id) {
        var res = receitaService.getById(id);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody ReceitaDTO dto) {
        try {
            var res = receitaService.update(id, dto);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        try {
            receitaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
