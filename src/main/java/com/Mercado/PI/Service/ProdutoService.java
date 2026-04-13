package com.Mercado.PI.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.DTO.PrecoComMercadoDTO;
import com.Mercado.PI.DTO.ProdutoFormularioDTO;
import com.Mercado.PI.DTO.ProdutoRespostaDTO;
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
    
    public List<ProdutoRespostaDTO> buscarProdutoContendoNome(String nome){ //Aqui procuramos por um produto que contém o nome completo ou parte dele
        
        List<ProdutoMercadoEntity> lista = produtoMercadoRepository.findByProduto_NomeContainingIgnoreCase(nome);

        //Inicio do mapa dos produtos por mercado e precos. O Map vai organizando os objetos assim que o loop (for) vai acontecendo
        Map<Integer, ProdutoRespostaDTO> mapa = new LinkedHashMap<>();

        for(ProdutoMercadoEntity productMarket : lista){

            //TRATAMENTO PARA OS ATRIBUTOS DE PRODUTO

            //Obtendo o ProdutoEntity da lista e o id do mesmo
            ProdutoEntity produto = productMarket.getProduto();
            Integer produtoId = produto.getId();

            //Cria o produto no mapa se ele não existir, ou reutiliza se existir
            ProdutoRespostaDTO produtoDTO = mapa.computeIfAbsent(produtoId, 
                
                id -> {
                    ProdutoRespostaDTO novoProdutoResposta = new ProdutoRespostaDTO();
                    
                    novoProdutoResposta.setNomeProduto(produto.getNome());
                    novoProdutoResposta.setTipo(produto.getTipo());
                    novoProdutoResposta.setCategoria(produto.getCategoria());
                    novoProdutoResposta.setMarca(produto.getMarca());
                    novoProdutoResposta.setUdMedida(produto.getUdMedida());
                    novoProdutoResposta.setQuantidadeConteudo(produto.getQuantidadeConteudo());
                    novoProdutoResposta.setStatus(produto.isStatus());
                    novoProdutoResposta.setPrecosMercados(new ArrayList<>());

                    return novoProdutoResposta;
                });

                //TRATAMENTO PARA PREÇOS E MERCADO

                PrecoComMercadoDTO precoMercado = new PrecoComMercadoDTO();

                precoMercado.setBairro(productMarket.getMercado().getBairro());
                precoMercado.setRedMercado(productMarket.getMercado().getRedMercado().getNomeRed());
                precoMercado.setPreco(productMarket.getPreco());
                precoMercado.setMoeda(productMarket.getMoeda());
                precoMercado.setDataPreco(productMarket.getDataPreco());
                precoMercado.setDataValidade(productMarket.getDataValidade());

                //Adicionando a lista de precosMercados (List<PrecoComMercadoDTO>) o novo preço e mercado
                //MUITO IMPORTANTE: Estou pegando a lista que já existe (nem que seja vazia) e estou adicionando o objeto PrecoComMercado à lista, é por isso que uso o getPrecosMercados, para pegar a lista e adicionar o objeto
                produtoDTO.getPrecosMercados().add(precoMercado);

        }

        //Resposta a ser enviada, converte o Map em List
        List<ProdutoRespostaDTO> resposta = new ArrayList<>(mapa.values());

        return resposta;
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
