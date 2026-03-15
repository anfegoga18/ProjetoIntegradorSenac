package com.Mercado.PI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Repository.MercadoRepository;


/**
 *
 * @author Andrés Felipe González García
 */

@Service
public class MercadoService {

    @Autowired
    private MercadoRepository mercadoRepository;
    
    
    public List<MercadoEntity> listarMercados(){
        return mercadoRepository.findAll();
    }
    
    public Optional<MercadoEntity> buscarMercadoPorId(int id){ //O Optional é caso não ache o Mercado já faz o gerenciamento
        return mercadoRepository.findById(id);
    }
    
    public MercadoEntity salvar(MercadoEntity mercado){
        return mercadoRepository.save(mercado);
    }

    public void excluir(int id){
        mercadoRepository.deleteById(id);
    }
}
