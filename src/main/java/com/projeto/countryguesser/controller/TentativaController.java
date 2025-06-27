package com.projeto.countryguesser.controller;

import com.projeto.countryguesser.entities.Tentativa;
import com.projeto.countryguesser.service.TentativaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tentativas")
public class TentativaController {

    private final TentativaService tentativaService;

    public TentativaController(TentativaService tentativaService) {
        this.tentativaService = tentativaService;
    }

    @PostMapping
    public Tentativa criar(@RequestParam Long usuarioId,
                           @RequestParam String resposta,
                           @RequestParam boolean acertou) {
        return tentativaService.criarTentativa(usuarioId, resposta, acertou);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Tentativa> listarPorUsuario(@PathVariable Long usuarioId) {
        return tentativaService.listarTentativasPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public Tentativa atualizar(@PathVariable Long id, @RequestBody Tentativa novaTentativa) {
        return tentativaService.atualizarTentativa(id, novaTentativa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        tentativaService.deletarTentativa(id);
    }
}
