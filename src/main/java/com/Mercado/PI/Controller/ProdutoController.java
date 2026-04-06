package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mercado.PI.DTO.ProdutoFormularioDTO;
import com.Mercado.PI.Service.MercadoService;
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

    @Autowired
    private MercadoService mercadoService;
    
    /* Esta parte apresenta a tela para fazer o cadastro de um produto*/
    @GetMapping("/cadastroProdutos")
    public String formularioProduto(Model model){
        
        ProdutoFormularioDTO produtoForm = new ProdutoFormularioDTO();//Cria uma instância do produtoForm para adicionar ao modelo do formulário
        model.addAttribute("ProdutoForm", produtoForm);//MUITO IMPORTANTE, essa chave "ProdutoForm" é a que deve coincidir com quem é chamado no HTML
        model.addAttribute("Mercados", mercadoService.listarMercados()); //Adicionando a lista de mercados cadastrados
        return "cadastroProduto";
    }

    /* Esta parte envia os dados inseridos pelo usuário para mostrar o produto */
    @PostMapping("/cadastroProdutos")
    public String processarFormProduto(@ModelAttribute ProdutoFormularioDTO produtoForm, Model model){//O @ModelAttribute significa que o que vai ser passado no Model é um objeto ProdutoFormularioDTO chamado ProdutoFom
        
        
        produtoForm = produtoService.salvar(produtoForm); //Persistindo o produto no BD, salvando a relacação na tabela intermadiária e retornando o DTO atualizado com os dados do mercado desde o service
        model.addAttribute("ProdutoForm", produtoForm);//Adicionando o ProdutoForm ATUALIZADO! ao modelo para posterior exibição na vista de CadastroProdutoSucesso
        
    return"cadastroProdutoSucesso";
    }
    

}
