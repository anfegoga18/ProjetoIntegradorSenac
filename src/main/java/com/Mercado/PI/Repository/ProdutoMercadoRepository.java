package com.Mercado.PI.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Data.ProdutoMercadoEntity;

/**
 *
 * @author Andrés Felipe González García
 */
@Repository
public interface  ProdutoMercadoRepository extends JpaRepository<ProdutoMercadoEntity, Integer>{

//Método para criar uma relacão produtoMercado apenas se não existe uma com o mesmo produto, mercado e data do preço igual
Optional <ProdutoMercadoEntity> findByProdutoAndMercadoAndDataPreco(ProdutoEntity produto, MercadoEntity mercado, LocalDate dataPreco);


}
