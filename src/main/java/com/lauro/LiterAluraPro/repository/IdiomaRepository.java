package com.lauro.LiterAluraPro.repository;

import com.lauro.LiterAluraPro.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {

    Optional<Idioma> findByNome(String nome);
}
