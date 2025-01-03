package com.alura.forum.forumhub.domain.topico;

import com.alura.forum.forumhub.domain.status.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String autor,
        @NotBlank
        String curso,
        @NotNull
        LocalDateTime dataCriacao,
        Status status) {

    public DadosCadastroTopico{
        if (dataCriacao == null){
            dataCriacao = LocalDateTime.now();
        }

        if (status == null){
            status = status.ABERTO;
        }
    }


}
