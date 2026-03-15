package com.Mercado.PI.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Mercado.PI.Data.MercadoEntity;

/**
 *
 * @author Andrés Felipe González García
 */

@Repository
public interface MercadoRepository extends JpaRepository<MercadoEntity, Integer> {

}
