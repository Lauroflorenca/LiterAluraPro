package com.lauro.LiterAluraPro.model;

import com.lauro.LiterAluraPro.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private int nascimento;

    private int falecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();


    public Autor(){}

    public Autor(String autor){
        this.nome = autor;
    }

    public Autor(AutorDTO autorDto){
        this.nome = autorDto.nome();
        this.nascimento = autorDto.nascimento();
        this.falecimento = autorDto.falecimento();
    }

    public Autor(Autor autor) {
        this.id = autor.id;
        this.nome = autor.nome;
        this.nascimento = autor.nascimento;
        this.falecimento = autor.falecimento;
        this.livros = autor.livros;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(int falecimento) {
        this.falecimento = falecimento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", falecimento=" + falecimento +
                '}';
    }
}
