package com.Mercado.PI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercado.PI.Data.ProdutoMercadoEntity;

/**
 *
 * @author Andrés Felipe González García
 */
@Repository
public interface  ProdutoMercadoRepository extends JpaRepository<ProdutoMercadoEntity, Integer>{

}
