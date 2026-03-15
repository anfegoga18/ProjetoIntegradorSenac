package com.Mercado.PI.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Mercado.PI.Data.ProdutoEntity;

/**
 *
 * @author Andrés Felipe González García
 */
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {

}
