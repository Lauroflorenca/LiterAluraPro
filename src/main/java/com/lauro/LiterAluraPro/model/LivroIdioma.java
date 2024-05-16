package com.lauro.LiterAluraPro.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "livro_idioma")
public class LivroIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Set<Idioma> get() {
        return Set.of();
    }
}