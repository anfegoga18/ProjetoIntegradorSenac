package com.Mercado.PI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.Data.RedEntity;
import com.Mercado.PI.Repository.RedRepository;



/**
 *
 * @author Andrés Felipe González García
 */

@Service
public class RedService {

    @Autowired
    private RedRepository redRepository;
    
    
    public List<RedEntity> listarRedesMercados(){
        return redRepository.findAll();
    }

    public Optional <RedEntity> buscarRedPorId(int id){
        return redRepository.findById(id);
    }
    
    public Optional<RedEntity> buscarRedPorNome(String nomeRed){ //O Optional é caso não ache a Red já faz o gerenciamento
        return redRepository.findByNomeRedIgnoreCase(nomeRed);
    }
    
    public RedEntity salvar(RedEntity redMercado){

        //Primeiro vou conferir se já existe o mercado usando o bairro, cidade e cep antes de cadastrar um novo
        Optional <RedEntity> existeRed = redRepository.findByNomeRedIgnoreCase(redMercado.getNomeRed());
        
        if(!existeRed.isEmpty()){
            throw new RuntimeException("Existe já uma red de mercados com esse nome");
        } else {
            return redRepository.save(redMercado);
        }
        
    }

    public void excluir(int id){
        redRepository.deleteById(id);
    }
}

