package com.alura.forum.forumhub.domain.topico;

import com.alura.forum.forumhub.domain.status.Status;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String autor, String curso,
                                      Status status) {

    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getAutor(), topico.getCurso(), topico.getStatus());
    }

}
