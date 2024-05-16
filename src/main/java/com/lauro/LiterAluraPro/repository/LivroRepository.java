package com.lauro.LiterAluraPro.repository;

import com.lauro.LiterAluraPro.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findAll();

    List<Livro> findAllByIdiomasNomeContainingIgnoreCase(String nome);

    List<Livro> findTop10ByOrderByDownloadsDesc();

    Optional<Livro> findByNome(String nome);
}
