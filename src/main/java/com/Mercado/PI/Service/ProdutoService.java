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

    public Optional<ProdutoEntity> buscarProdutoPorId(Integer id){ //O Optional é caso não ache o Produto já faz o gerenciamento
        return produtoRepository.findById(id);
    }
    
    public ProdutoFormularioDTO salvar(ProdutoFormularioDTO produtoForm){

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

        //System.out.println("****************** El mercado nuevo es: "  + produto);
        produtoRepository.save(produto); //Salvando o produto no BD

        //Para a o Mercado

        MercadoEntity mercado = mercadoRepository.findById(produtoForm.getMercadoId()).orElseThrow();
            //Atualizando o DTO para enviar na view a parte do mercado
            produtoForm.setBairro(mercado.getBairro());
            produtoForm.setRedMercado(mercado.getRedMercado().getNomeRed());


        //Para o ProdutoMercado
        
        ProdutoMercadoEntity produtoMercado = produtoMercadoRepository.findByProdutoAndMercadoAndDataPreco(produto, mercado, produtoForm.getDataPreco())
                                                                        .orElseGet( 
                                                                            
                                                                            () -> {

                                                                            ProdutoMercadoEntity novoProdutoMercado = new ProdutoMercadoEntity();
                                                                            novoProdutoMercado.setProduto(produto);
                                                                            novoProdutoMercado.setMercado(mercado);
                                                                            // Crio o novoProtudoMercado sem o preço e os outros atributos para que possa modificar ou atulizar depois, pode acontecer de não ter de criar uma nova relação e apenas fazer uma atualização
                                                                            return novoProdutoMercado; //Deve existir um return para não ter erro, se existe { } deve existir um return
 
                                                                        });

        produtoMercado.setPreco(produtoForm.getPreco());
        produtoMercado.setMoeda(produtoForm.getMoeda());
        produtoMercado.setDataPreco(produtoForm.getDataPreco());
        produtoMercado.setDataValidade(produtoForm.getDataValidade());

        produtoMercado = produtoMercadoRepository.save(produtoMercado); //Salvando os dados na tabela intermediária
        //System.out.println("*************** El mercado nuevo es: "  + produtoMercado);
        
        
        return produtoForm;

    }

    public void excluir(Integer id){ //Deve ser testado se não vai apagar o mercado na hora de excluir o produto
        produtoRepository.deleteById(id);
    }

}
