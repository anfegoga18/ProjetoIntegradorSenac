package com.Mercado.PI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.DTO.ProdutoFormularioDTO;
import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Data.ProdutoMercadoEntity;
import com.Mercado.PI.Repository.MercadoRepository;
import com.Mercado.PI.Repository.ProdutoMercadoRepository;
import com.Mercado.PI.Repository.ProdutoRepository;

/**
 *
 * @author Andrés Felipe González García
 */

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private ProdutoMercadoRepository produtoMercadoRepository;
    
    
    public List<ProdutoEntity> listarProdutos(){
        return produtoRepository.findAll();
    }
    
    public List<ProdutoEntity> buscarProdutoContendoNome(String nome){ //Aqui procuramos por um produto que contém o nome completo ou parte dele
        
        /* 
        List<ProdutoEntity> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        if(produtos.isEmpty()){
            throw new EntityNotFoundException("Produto não encontrado");
        }*/

        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<ProdutoEntity> buscarProdutoPorId(int id){ //O Optional é caso não ache o Produto já faz o gerenciamento
        return produtoRepository.findById(id);
    }
    
    public ProdutoMercadoEntity salvar(ProdutoFormularioDTO produtoForm){

        //Para o Produto, busca primeiro se o produto exite no banco de dados para reutilizar, se não for achado vai criar um novo produto no banco
        ProdutoEntity produto = produtoRepository.findByNomeIgnoreCaseAndTipoIgnoreCaseAndCategoriaIgnoreCaseAndMarcaIgnoreCaseAndUdMedidaIgnoreCaseAndQuantidadeConteudoAndStatus(
                                                                    produtoForm.getNome(), 
                                                                    produtoForm.getTipo(), 
                                                                    produtoForm.getCategoria(), 
                                                                    produtoForm.getMarca(), 
                                                                    produtoForm.getUdMedida(), 
                                                                    produtoForm.getQuantidadeConteudo(), 
                                                                    produtoForm.isStatus()
                                                                ).orElseGet(
                                                                    () -> {

                                                                    ProdutoEntity produtoNovo = new ProdutoEntity();

                                                                    produtoNovo.setNome(produtoForm.getNome());
                                                                    produtoNovo.setTipo(produtoForm.getTipo());
                                                                    produtoNovo.setCategoria(produtoForm.getCategoria());
                                                                    produtoNovo.setMarca(produtoForm.getMarca());
                                                                    produtoNovo.setUdMedida(produtoForm.getUdMedida());
                                                                    produtoNovo.setQuantidadeConteudo(produtoForm.getQuantidadeConteudo());
                                                                    produtoNovo.setStatus(produtoForm.isStatus());

                                                                    return produtoNovo;

                                                                });

        produtoRepository.save(produto); //Salvando o produto

        //Para a o Mercado

        MercadoEntity mercado = mercadoRepository.findById(produtoForm.getMercadoId()).orElseThrow();

        //Para o ProdutoMercado
        
        ProdutoMercadoEntity produtoMercado = new ProdutoMercadoEntity();
        produtoMercado.setProduto(produto);
        produtoMercado.setMercado(mercado);
        produtoMercado.setPreco(produtoForm.getPreco());
        produtoMercado.setMoeda(produtoForm.getMoeda());
        produtoMercado.setDataPreco(produtoForm.getDataPreco());
        produtoMercado.setDataValidade(produtoForm.getDataValidade());

        return produtoMercadoRepository.save(produtoMercado);

    }

    public void excluir(int id){ //Deve ser testado se não vai apagar o mercado na hora de excluir o produto
        produtoRepository.deleteById(id);
    }

}
