package com.Mercado.PI.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercado.PI.Data.ProdutoEntity;

/**
 *
 * @author Andrés Felipe González García
 */
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome); // Este método é para procurar por uma parte de um nome de um produto

    //Este método que procurar um produto com todos os atributos, caso já esteja no BD
    //Optional<ProdutoEntity> findByAllAttributes(String nome, String tipo, String categoria, String marca, String udMedida, double quantidadeConteudo, boolean status);

    Optional<ProdutoEntity> findByNomeIgnoreCaseAndTipoIgnoreCaseAndCategoriaIgnoreCaseAndMarcaIgnoreCaseAndUdMedidaIgnoreCaseAndQuantidadeConteudoAndStatus(String nome, String tipo, String categoria, String marca, String udMedida, double quantidadeConteudo, boolean status);

}
