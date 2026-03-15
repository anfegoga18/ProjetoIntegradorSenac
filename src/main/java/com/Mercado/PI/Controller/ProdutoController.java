package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Service.ProdutoService;


/**
 *
 * @author Andrés Felipe González García
 */
@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    
    /*
    <a th:href="@{/produtos/cadastroProdutos}">Cadastro de Produto</a>
    <a th:href="@{/produtos/listadoProdutos}">Lista de Produtos</a>
    */
    
    @Autowired
    private ProdutoService produtoService;
    
    /* Esta parte apresenta a tela para fazer o cadastro de um produto*/
    @GetMapping("/cadastroProdutos")
    public String formularioProduto(Model model){
        
        ProdutoEntity produto = new ProdutoEntity();//Cria uma instância do produto para adicionar ao modelo do formulário
        model.addAttribute("Produto", produto);//MUITO IMPORTANTE, essa chave "Produto" é a que deve coincidir com quem é chamado no HTML
        
        return "cadastroProduto";
    }

    /* Esta parte envia os dados inseridos pelo usuário para mostrar o produto */
    @PostMapping("/cadastroProdutos")
    public String processarFormProduto(@ModelAttribute ProdutoEntity produto, Model model){//O @ModelAttribute significa que o que vai ser passado no Model é um objeto ProdutoEntity chamado produto
        
        produtoService.salvar(produto); //Persistindo o produto no BD
        model.addAttribute("Produto", produto);//Adicionando o produto ao modelo para posterior exibição na vista de análise
        
    return"cadastroProdutoSucesso";
    }
    

}
