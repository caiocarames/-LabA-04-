package br.caio_carames.acervo.repositorio;

import br.caio_carames.acervo.entidade.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAutorIgnoreCase(String autor);
    List<Livro> findByAnoPublicacao(int anoPublicacao);
    List<Livro> findByTituloContainingIgnoreCase(String termo);
    boolean existsByTituloIgnoreCaseAndAutorIgnoreCase(String titulo, String autor);
}
