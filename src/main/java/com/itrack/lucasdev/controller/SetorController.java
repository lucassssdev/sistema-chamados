package com.itrack.lucasdev.controller;

import com.itrack.lucasdev.model.Setor;
import com.itrack.lucasdev.service.SetorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    public Setor salvarSetor(@RequestBody Setor setor) {
        return setorService.salvarSetor(setor);
    }

    @GetMapping
    public List<Setor> listar() {
        return setorService.listarSetor();
    }

    @GetMapping("/{id}")
    public Setor buscarSetor(@PathVariable Integer id) {
        return setorService.buscarSetor(id);
    }

    @PatchMapping("/{id}")
    public Setor atualizar(@PathVariable Integer id, @RequestBody Setor setor) {
        return setorService.atualizar(id, setor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        setorService.deletar(id);
    }
}
