package com.alura.forum.forumhub.domain.topico;

import com.alura.forum.forumhub.domain.status.Status;

public record DadosAtualizacaoTopico(
        String titulo,
        String mensagem,
        Status status,
        String curso) {
}
