package com.Mercado.PI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Repository.ProdutoRepository;

/**
 *
 * @author Andrés Felipe González García
 */

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    
    public List<ProdutoEntity> listarProdutos(){
        return produtoRepository.findAll();
    }
    
    public Optional<ProdutoEntity> buscarProdutoPorId(int id){ //O Optional é caso não ache o Produto já faz o gerenciamento
        return produtoRepository.findById(id);
    }
    
    public ProdutoEntity salvar(ProdutoEntity produto){
        return produtoRepository.save(produto);
    }

    public void excluir(int id){
        produtoRepository.deleteById(id);
    }

}
