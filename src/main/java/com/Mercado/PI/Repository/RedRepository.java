package com.Mercado.PI.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercado.PI.Data.RedEntity;

/**
 *
 * @author Andrés Felipe González García
 */

@Repository
public interface RedRepository extends JpaRepository<RedEntity, Integer> {

    Optional <RedEntity> findByNomeRedIgnoreCase(String nomeRed);

}

