package com.projeto.countryguesser.service;

import com.projeto.countryguesser.entities.Tentativa;
import com.projeto.countryguesser.entities.Usuario;
import com.projeto.countryguesser.repository.TentativaRepository;
import com.projeto.countryguesser.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TentativaService {

    private final TentativaRepository tentativaRepository;
    private final UsuarioRepository usuarioRepository;

    public TentativaService(TentativaRepository tentativaRepository, UsuarioRepository usuarioRepository) {
        this.tentativaRepository = tentativaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Tentativa criarTentativa(Long usuarioId, String resposta, boolean acertou) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Tentativa tentativa = new Tentativa();
        tentativa.setResposta(resposta);
        tentativa.setAcertou(acertou);
        tentativa.setDataHora(LocalDateTime.now());
        tentativa.setUsuario(usuario);

        return tentativaRepository.save(tentativa);
    }

    public List<Tentativa> listarTentativasPorUsuario(Long usuarioId) {
        return tentativaRepository.findByUsuarioId(usuarioId);
    }

    public Tentativa buscarPorId(Long id) {
        return tentativaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tentativa não encontrada"));
    }

    public Tentativa atualizarTentativa(Long id, Tentativa novaTentativa) {
        Tentativa tentativa = buscarPorId(id);
        tentativa.setResposta(novaTentativa.getResposta());
        tentativa.setAcertou(novaTentativa.isAcertou());
        tentativa.setDataHora(novaTentativa.getDataHora());
        return tentativaRepository.save(tentativa);
    }

    public void deletarTentativa(Long id) {
        tentativaRepository.deleteById(id);
    }
}
