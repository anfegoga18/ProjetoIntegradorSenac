package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
