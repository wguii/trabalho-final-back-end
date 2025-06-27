package com.projeto.countryguesser.service;

import com.projeto.countryguesser.dto.PaisDTO;
import com.projeto.countryguesser.dto.RestCountriesResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaisService {

    private final TentativaService tentativaService;

    public PaisService(TentativaService tentativaService) {
        this.tentativaService = tentativaService;
    }
    private PaisDTO paisAtual;
    private List<String> dicasUsadas = new ArrayList<>();  // Lista de dicas já usadas

    public PaisDTO gerarPaisAleatorio() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restcountries.com/v3.1/all?fields=name,region,capital,languages,currencies,population";
        ResponseEntity<RestCountriesResponseDTO[]> response =
                restTemplate.getForEntity(url, RestCountriesResponseDTO[].class);

        RestCountriesResponseDTO[] paises = response.getBody();

        // Selecionando um país aleatório
        int paisAleatorioIndex = (int) (Math.random() * paises.length);
        RestCountriesResponseDTO paisApi = paises[paisAleatorioIndex];

        PaisDTO pais = new PaisDTO();
        pais.setName(paisApi.getName().getCommon());
        pais.setRegion(paisApi.getRegion());
        pais.setCapital(paisApi.getCapital());
        pais.setLanguages(paisApi.getLanguages());
        pais.setCurrencies(paisApi.getCurrencies());

        // Gerando as dicas
        List<String> dicas = new ArrayList<>();
        dicas.add("Capital: " + pais.getCapital());
        dicas.add("Região: " + pais.getRegion());
        dicas.add("Idioma principal: " + pais.getLanguages().values().iterator().next());
        dicas.add("Moeda: " + pais.getCurrencies().values().iterator().next().getName());

        pais.setDicas(dicas);  // Associando as dicas ao país

        paisAtual = pais;  // Armazenando o país atual

        return pais;
    }

    public String obterDica() {
        if (paisAtual == null || paisAtual.getDicas().isEmpty()) {
            return "Erro! Não há dicas disponíveis.";
        }

        // Pegando uma dica aleatória que ainda não foi usada

        List<String> dicasDisponiveis = new ArrayList<>(paisAtual.getDicas());
        dicasDisponiveis.removeAll(dicasUsadas);
        if (dicasDisponiveis.isEmpty()) {
            return "Você já usou todas as dicas!";
        }

        int dicaIndex = (int) (Math.random() * dicasDisponiveis.size());
        String dica = dicasDisponiveis.get(dicaIndex);

        dicasUsadas.add(dica);  // Marcando a dica como usada

        return dica;
    }

    public boolean verificarResposta(String resposta) {
        if (paisAtual == null) {
            return false;
        }
        return paisAtual.getName().equalsIgnoreCase(resposta);
    }

    public void registrarTentativa(Long usuarioId, String resposta, boolean acertou) {
        tentativaService.criarTentativa(usuarioId, resposta, acertou);
    }
}
