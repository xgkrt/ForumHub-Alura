package com.alura.forum.forumhub.controller;

import com.alura.forum.forumhub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("topicos")
public class TopicoController {


    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){

        if (topicoRepository.existsByTitulo(dados.titulo())){
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título.");
        }
        if (topicoRepository.existsByMensagem(dados.mensagem())){
            throw new IllegalArgumentException("Já existe um tópico com a mesma mensagem.");
        }

        var topico = new Topico(dados);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));

    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao){
        var page = topicoRepository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarDetalhes(@PathVariable Long id){
        var topico = topicoRepository.findById(id);
        if (topico.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", "Tópico não foi encontrado."));
        }
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        if (dados.titulo() != null && topicoRepository.existsByTitulo(dados.titulo())) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título.");
        }
        if (dados.mensagem() != null && topicoRepository.existsByMensagem(dados.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com a mesma mensagem.");
        }

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir (@PathVariable Long id){
        var topico = topicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

        topico.excluir();
        return ResponseEntity.noContent().build();
    }
}
