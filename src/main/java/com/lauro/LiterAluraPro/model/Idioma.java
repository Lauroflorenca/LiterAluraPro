package com.lauro.LiterAluraPro.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "idioma")

public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "idiomas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Livro> livros = new HashSet<>();


    public Idioma() {
    }

    public Idioma(String nome) {
        this.nome = nome.replaceAll("'", "");
    }

    public Idioma(Idioma idioma) {
        this.id = idioma.id;
        this.nome = idioma.nome;
        this.livros = idioma.livros;
    }


    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        livro.getIdiomas().add(this); // Adiciona o idioma atual à lista de idiomas do livro
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return  nome  ;
    }
}
