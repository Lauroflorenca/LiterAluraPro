package com.lauro.LiterAluraPro.principal;

import com.lauro.LiterAluraPro.dto.AutorDTO;
import com.lauro.LiterAluraPro.model.*;
import com.lauro.LiterAluraPro.repository.AutorRepository;
import com.lauro.LiterAluraPro.repository.IdiomaRepository;
import com.lauro.LiterAluraPro.repository.LivroRepository;
import com.lauro.LiterAluraPro.service.ConsumoApi;
import com.lauro.LiterAluraPro.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private LivroRepository repositorioLivro;
    private AutorRepository repositorioAutor;
    private IdiomaRepository repositorioIdioma;

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private String endereco;

    //Bases consulta
    private final String URL_BASE_GT = "https://gutendex.com/books?search="; //casa%20velha
    private final String URL_BASE_OL = "https://openlibrary.org/search.json?q="; // pai+rico
    private final String URL_BASE_AUTHOR = "https://openlibrary.org/search/authors.json?q="; // pai+rico

    public Principal() {}

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor, IdiomaRepository repositorioIdioma) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
        this.repositorioIdioma = repositorioIdioma;
    }


    public void exibeMenu() {
        var opcao = -1;

        while (opcao!= 9) {
            var menu = """
                    
                    █     ▀  ▀▀█▀▀ █▀▀ █▀▀█ █▀▀█ █   █  █ █▀▀█ █▀▀█ 　 ▒█▀▀█ █▀▀█ █▀▀█\s
                    █    ▀█▀   █   █▀▀ █▄▄▀ █▄▄█ █   █  █ █▄▄▀ █▄▄█ 　 ▒█▄▄█ █▄▄▀ █  █\s
                    █▄▄█ ▀▀▀   ▀   ▀▀▀ ▀ ▀▀ █  █ ▀▀▀ ▀▀▀▀ ▀ ▀▀ ▀  ▀ 　 ▒█    ▀ ▀▀ ▀▀▀▀
                   
                         ▒ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ▒
                         ▒                                                    ▒
                         ▒   ░ 0- Buscar livro por título  (openlibrary)      ▒
                         ▒   ░ 1- Buscar livro por título  (gutendex)         ▒
                         ▒                                                    ▒
                         ▒   ░ 2- Listar livros registrados                   ▒
                         ▒   ░ 3- Listar autores registrados                  ▒
                         ▒   ░ 4- Listar autores vivos em um determinado ano  ▒
                         ▒   ░ 5- Listar livros em um determinado idioma      ▒
                         ▒   ░ 6- Top 10 Livros mais baixados                 ▒
                         ▒                                                    ▒
                         ▒   ░ 9- Sair                                        ▒
                         ▒                                                    ▒
                         ▒ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ ▒
                   
                         Opção -> """;
            System.out.println(menu);

            while (!leitura.hasNextInt()) {
                System.out.println("Por favor, digite um ano válido:");
                leitura.next();
            }

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 0:
                    buscaLivroOpen();
                    break;
                case 1:
                    buscaLivro();
                    break;
                case 2:
                    livrosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivosAno();
                    break;
                case 5:
                    livroPorIdioma();
                    break;
                case 6:
                    top10MaisBaixados();
                    break;


                case 9:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void top10MaisBaixados() {
        List<Livro> livros = repositorioLivro.findTop10ByOrderByDownloadsDesc();

        if(!livros.isEmpty()){
            final int[] contt = {1};
            livros.forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Livro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, contt[0]++, l.getNome(), l.getAutor().toString(), l.getIdiomas().toString()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- Não há livros cadastrados no banco! ----------------------------------------"
            );
        }
    }

    public void buscaLivroOpen(){
        System.out.println("Informe o nome do livro: ");
        String nomeLivro = leitura.nextLine();
        nomeLivro = nomeLivro.trim().replaceAll(" ", "+");

        endereco = URL_BASE_OL + nomeLivro;
        String json = consumo.obterDados(endereco);

        RLivro listaRt = conversor.obterDados(json, RLivro.class);

        if(listaRt.results().isEmpty()){
            System.out.println("---------------------- NENHUM LIVRO ENCONTRADO ----------------------");
        }else{
            System.out.println("---------------------- LIVRO(S) ENCONTRADO(S) ----------------------\n");

            final int[] cont = {1};
            listaRt.results().forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Livro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, cont[0]++, l.nome(), (l.autorNome() != null) ? l.autorNome() : "Não encontrado", (l.idiomas() != null && !l.idiomas().isEmpty()) ? l.idiomas().toString() : "Não encontrado"
                );
            });

            System.out.println("\nInforme o número do livro: ");
            int numLivro = leitura.nextInt();

            while (numLivro < 1 || numLivro > cont[0]) {
                System.out.println("Valor não encontrado!\n");
                numLivro = leitura.nextInt();
            }

            numLivro--;

            //VERIFICA SE O LIVRO JÁ ESTÁ CADASTRADO
            Optional<Livro> verificaLivro = repositorioLivro.findByNome(listaRt.results().get(numLivro).nome());
            if(verificaLivro.isEmpty()) {
                Livro livro = new Livro();
                livro.setNome(listaRt.results().get(numLivro).nome());
                livro.setDownloads(listaRt.results().get(numLivro).downloads());
                livro.setFavorito(false);


                Autor autor = new Autor("Não encontrado.");
                if (listaRt.results().get(numLivro).autorNome() != null &&
                        !listaRt.results().get(numLivro).autorNome().isEmpty()) {

                    //VERIFICA SE AUTOR JÁ ESTÀ CADASTRADO
                    Optional<Autor> verificaAutor = repositorioAutor.findByNome(listaRt.results().get(numLivro).autorNome().get(0).getNome());
                    if(verificaAutor.isEmpty()) {
                        String autorAjustado = listaRt.results().get(numLivro).autorNome().get(0).getNome();
                        autorAjustado = autorAjustado.replaceAll(" ", "+");
                        //OBTEM NASCIMENTO AUTOR
                        endereco = URL_BASE_AUTHOR + autorAjustado;
                        json = consumo.obterDados(endereco);
                        AutorDTO autorResult = conversor.obterDados(json, AutorDTO.class);

                        if (autorResult.nascimentoTexto() != null && !autorResult.nascimentoTexto().isEmpty()) {
                            var s = autorResult.nascimentoTexto().split(" ").length;
                            s = s == 3 ? Integer.parseInt(autorResult.nascimentoTexto().split(" ")[2]) : 0;
                            autor.setFalecimento(s);
                        }
                        autor.setNome(listaRt.results().get(numLivro).autorNome().get(0).getNome());
                    }else{
                        autor = new Autor(verificaAutor.get());
                    }
                }

                Idioma idioma = new Idioma("Não encontrado.");
                //VERIFICA SE AUTOR JÁ ESTÀ CADASTRADO
                Optional<Idioma> verificaIdioma = repositorioIdioma.findByNome(listaRt.results().get(numLivro).idiomas().get(0).getNome());
                if(verificaIdioma.isEmpty()) {
                    if (!listaRt.results().get(numLivro).idiomas().isEmpty()) {
                        idioma = new Idioma(listaRt.results().get(numLivro).idiomas().get(0).getNome());
                    }
                }else{
                    idioma = new Idioma(verificaIdioma.get());
                }

                repositorioAutor.save(autor);
                repositorioIdioma.save(idioma);

                livro.adicionarIdioma(idioma);
                livro.setAutor(autor);
                repositorioLivro.save(livro);

                System.out.println("-------------------------------- O Livro foi registrado com sucesso! --------------------------------");
            }else{
                System.out.println("-------------------------------- O Livro já está registrado no banco! --------------------------------");
            }
        }

    }

    public void buscaLivro(){
        System.out.println("Informe o nome do livro: ");
        String nomeLivro = leitura.nextLine();
            nomeLivro = nomeLivro.trim().replaceAll(" ", "%20");

        endereco = URL_BASE_GT + nomeLivro;
        String json = consumo.obterDados(endereco);

        RLivro listaRt = conversor.obterDados(json, RLivro.class);

        if(listaRt.results().isEmpty()){
            System.out.println("---------------------- NENHUM LIVRO ENCONTRADO ----------------------");
        }else{
            System.out.println("---------------------- LIVRO(S) ENCONTRADO(S) ----------------------\n");
            final int[] cont = {1};
            listaRt.results().forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Livro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, cont[0]++, l.nome(), (l.autorNome() != null) ? l.autorNome() : "Não encontrado", (l.idiomas() != null && !l.idiomas().isEmpty()) ? l.idiomas().toString() : "Não encontrado"
                );
            });

            System.out.println("\nInforme o número do livro: ");
            int numLivro = leitura.nextInt();

            while (numLivro < 1 || numLivro > cont[0]) {
                System.out.println("Valor não encontrado!\n");
                numLivro = leitura.nextInt();
            }

            numLivro--;

            //VERIFICA SE O LIVRO JÁ ESTÁ CADASTRADO
            Optional<Livro> verificaLivro = repositorioLivro.findByNome(listaRt.results().get(numLivro).nome());
            if(verificaLivro.isEmpty()) {


                Livro livro = new Livro();
                livro.setNome(listaRt.results().get(numLivro).nome());
                livro.setDownloads(listaRt.results().get(numLivro).downloads());
                livro.setFavorito(false);

                // VERIFICAR SE EXISTE UM AUTOR OU IDIOMA
                Autor autor = new Autor("Não encontrado.");
                if (listaRt.results().get(numLivro).autor() != null &&
                        !listaRt.results().get(numLivro).autor().isEmpty()) {

                    //VERIFICA SE AUTOR JÁ ESTÀ CADASTRADO
                    Optional<Autor> verificaAutor = repositorioAutor.findByNome(listaRt.results().get(numLivro).autor().get(0).nome());
                    if (verificaAutor.isEmpty()) {
                        autor = new Autor(listaRt.results().get(numLivro).autor().get(0));
                    }else{
                        autor = new Autor(verificaAutor.get());
                    }
                }


                //VERIFICA SE AUTOR JÁ ESTÀ CADASTRADO
                Idioma idioma = new Idioma("Não encontrado.");
                Optional<Idioma> verificaIdioma = repositorioIdioma.findByNome(listaRt.results().get(numLivro).idiomas().get(0).getNome());
                if(verificaIdioma.isEmpty()) {
                    if (!listaRt.results().get(numLivro).idiomas().isEmpty()) {
                        idioma = new Idioma(listaRt.results().get(numLivro).idiomas().get(0).getNome());
                    }
                }else{
                    idioma = new Idioma(verificaIdioma.get());
                    System.out.println("PASSOU AQUI1");
                }

                repositorioAutor.save(autor);
                repositorioIdioma.save(idioma);

                livro.adicionarIdioma(idioma);
                livro.setAutor(autor);
                repositorioLivro.save(livro);

                System.out.println("-------------------------------- O Livro foi registrado com sucesso! --------------------------------");
            }else{
                System.out.println("-------------------------------- O Livro já está registrado no banco! --------------------------------");
            }
        }

    }

    public void livrosRegistrados(){
        List<Livro> livros = repositorioLivro.findAll();

        if(!livros.isEmpty()){
            final int[] cont = {1};
            livros.forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Livro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, cont[0]++, l.getNome(), l.getAutor().toString(), l.getIdiomas().toString()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- Nenhum Livro Encontrado! ----------------------------------------"
            );
        }
}

    public void autoresRegistrados(){
        List<Autor> autores = repositorioAutor.findAll();

        if(!autores.isEmpty()){
            final int[] cont = {1};
            autores.forEach( a -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] Autor ----------------------------------------
                             Nome: %s
                             Nascimento: %s
                             Falecimento: %s
                            """, cont[0]++, a.getNome(), a.getNascimento(), a.getFalecimento()
                    );
                });
        }else{
            System.out.print(
                    "--------------------------------------- Nenhum Autor Encontrado! ----------------------------------------"
            );
        }
    }

    public void autoresVivosAno(){
        System.out.println("Informe o Ano: ");

        while (!leitura.hasNextInt()) {
            System.out.println("Por favor, digite um ano válido:");
            leitura.next();
        }

        int ano = leitura.nextInt();

        List<Autor> autores = repositorioAutor.verificaAutoVivoData(ano);

        if(!autores.isEmpty()){
            final int[] cont = {1};
            autores.forEach( a -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] Autor ----------------------------------------
                             Nome: %s
                             Nascimento: %s
                             Falecimento: %s
                            """, cont[0]++, a.getNome(), a.getNascimento(), a.getFalecimento()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- Nenhum Autor Encontrado! ----------------------------------------"
            );
        }
    }

    public void livroPorIdioma(){

        List<Idioma> idiomas = repositorioIdioma.findAll();

        if(!idiomas.isEmpty()){
            final int[] cont = {1};

            idiomas.forEach( i -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] %s ----------------------------------------
                            """, cont[0]++, i.getNome()
                );
            });

            System.out.println("\nInforme o Número Do Idioma Desejado: ");

            while (!leitura.hasNextInt()) {
                System.out.println("Por favor, digite um ano válido:");
                leitura.next();
            }

            int numIdioma = leitura.nextInt();

            while (numIdioma < 0 || numIdioma > cont[0]) {
                System.out.println("Valor não encontrado!\n");
                numIdioma = leitura.nextInt();
            }

            numIdioma--;

            List<Livro> livros = repositorioLivro.findAllByIdiomasNomeContainingIgnoreCase(idiomas.get(numIdioma).getNome());

            if(!livros.isEmpty()){
                final int[] contt = {1};
                livros.forEach( l -> {

                    System.out.printf(
                            """
                             ---------------------------------------- [%s] Livro ----------------------------------------
                             Título: %s
                             Autor: %s
                             Idioma(s): %s
                            """, contt[0]++, l.getNome(), l.getAutor().toString(), l.getIdiomas().toString()
                    );
                });
            }else{
                System.out.print(
                        "--------------------------------------- Nenhum Livro Encontrado! ----------------------------------------"
                );
            }

        }else{
            System.out.print(
                    "--------------------------------------- Nenhum Idioma Encontrado! ----------------------------------------"
            );
        }
    }
}
