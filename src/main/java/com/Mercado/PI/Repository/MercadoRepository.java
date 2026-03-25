package com.Mercado.PI.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mercado.PI.Data.MercadoEntity;

/**
 *
 * @author Andrés Felipe González García
 */

@Repository
public interface MercadoRepository extends JpaRepository<MercadoEntity, Integer> {

    Optional <MercadoEntity> findByBairroIgnoreCaseAndCepIgnoreCaseAndCidadeIgnoreCase(String bairro, String cep, String cidade);

}
