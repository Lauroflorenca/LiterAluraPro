package com.lauro.LiterAluraPro;

import com.lauro.LiterAluraPro.principal.Principal;
import com.lauro.LiterAluraPro.repository.IdiomaRepository;
import com.lauro.LiterAluraPro.repository.LivroRepository;
import com.lauro.LiterAluraPro.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraProApplication implements CommandLineRunner {

	@Autowired
	private LivroRepository repositorioLivro;

	@Autowired
	private AutorRepository repositorioAutor;

	@Autowired
	private IdiomaRepository repositorioIdioma;


	public static void main(String[] args) {
		SpringApplication.run(LiterAluraProApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLivro, repositorioAutor, repositorioIdioma);
		principal.exibeMenu();
	}
}
