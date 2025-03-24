// Caio Caramés - 10308718

package br.caio_carames.acervo.aplicacao;

import br.caio_carames.acervo.entidade.Livro;
import br.caio_carames.acervo.repositorio.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    @Autowired
    private LivroRepository livroRepository;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcao;
        do {
            System.out.println("\nMenu do Acervo");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Listar livros");
            System.out.println("3. Buscar por autor");
            System.out.println("4. Buscar por ano de publicação");
            System.out.println("5. Buscar por termo no título");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1 -> cadastrarLivro();
                case 2 -> listarLivros();
                case 3 -> buscarPorAutor();
                case 4 -> buscarPorAno();
                case 5 -> buscarPorTitulo();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarLivro() {
        System.out.println("\n[Cadastro de Livro]");
        System.out.print("Digite o título: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o autor: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o ano de publicação: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite a editora: ");
        String editora = scanner.nextLine();

        if (livroRepository.existsByTituloIgnoreCaseAndAutorIgnoreCase(titulo, autor)) {
            System.out.println("Livro já cadastrado.");
            return;
        }

        livroRepository.save(new Livro(titulo, autor, ano, editora));
        System.out.println("Livro cadastrado com sucesso!");
    }

    private void listarLivros() {
        System.out.println("\n[Listagem Completa do Acervo]");
        System.out.println("ID | Título          | Autor       | Ano | Editora");
        System.out.println("---------------------------------------------------------------");
        livroRepository.findAll().forEach(l -> 
            System.out.printf("%2d | %-28s | %-17s | %4d | %s\n", l.getId(), l.getTitulo(), l.getAutor(), l.getAnoPublicacao(), l.getEditora())
        );
    }

    private void buscarPorAutor() {
        System.out.print("\nDigite o nome do autor: ");
        String autor = scanner.nextLine();
        List<Livro> livros = livroRepository.findByAutorIgnoreCase(autor);
        System.out.println("\nLivros encontrados:");
        livros.forEach(l -> System.out.printf("- %s (%d, %s)\n", l.getTitulo(), l.getAnoPublicacao(), l.getEditora()));
    }

    private void buscarPorAno() {
        System.out.print("\nDigite o ano desejado: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        List<Livro> livros = livroRepository.findByAnoPublicacao(ano);
        System.out.println("\nLivros publicados em " + ano + ":");
        livros.forEach(l -> System.out.printf("- %s, por %s (%s)\n", l.getTitulo(), l.getAutor(), l.getEditora()));
    }

    private void buscarPorTitulo() {
        System.out.print("\nDigite o termo desejado: ");
        String termo = scanner.nextLine();
        List<Livro> livros = livroRepository.findByTituloContainingIgnoreCase(termo);
        System.out.println("\nLivros encontrados:");
        livros.forEach(l -> System.out.printf("- %s, por %s (%d)\n", l.getTitulo(), l.getAutor(), l.getAnoPublicacao()));
    }
}
