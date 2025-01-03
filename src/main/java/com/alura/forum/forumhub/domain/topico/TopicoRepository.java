package com.alura.forum.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    boolean existsByTitulo(String titulo);

    boolean existsByMensagem(String mensagem);

    Page<Topico> findAllByAtivoTrue(Pageable paginacao);
}