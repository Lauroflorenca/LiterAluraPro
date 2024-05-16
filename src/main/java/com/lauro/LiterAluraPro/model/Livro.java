package com.lauro.LiterAluraPro.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private boolean favorito;

    private int downloads;

    @ManyToOne
    private Autor autor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "livro_idioma",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id"))
    private Set<Idioma> idiomas = new HashSet<>();


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public void adicionarIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
        idioma.getLivros().add(this); // Para manter a consistência bidirecional
    }

    @Override
    public String toString() {
        return "Livro{" +
                "nome='" + nome + '\'' +
                ", autor=" + autor +
                ", idiomas=" + idiomas +
                '}';
    }
}
