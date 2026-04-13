package com.Mercado.PI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Mercado.PI.DTO.ProdutoRespostaDTO;
import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Service.ProdutoService;


/**
 *
 * @author Andrés Felipe González García
 */
@Controller
@RequestMapping("/")

public class PesquisaController {

    @Autowired
    private ProdutoService produtoService;
    
    /* Esta parte apresenta a tela para fazer uma pesquisa de produto*/
    @GetMapping("/")
    public String pesquisarProduto(Model model){
        
        ProdutoEntity produto = new ProdutoEntity();//Cria uma instância do produto apenas com o nome para adicionar ao modelo do formulário
        model.addAttribute("Produto", produto);//MUITO IMPORTANTE, essa chave "Produto" é a que deve coincidir com quem é chamado no HTML
        
        return "Index";
    }
    
    @PostMapping("/")
    public String mostrarProdutoPesquisado (@RequestParam String nome , Model model){
        
        List<ProdutoRespostaDTO> produtos = produtoService.buscarProdutoContendoNome(nome);

        if(produtos.isEmpty()){
            model.addAttribute("Mensagem", "Nenhum produto achado para: " + nome);
        }else{
            model.addAttribute("Produtos", produtos);
        }

        return "ListadoProdutos";
    }

}
