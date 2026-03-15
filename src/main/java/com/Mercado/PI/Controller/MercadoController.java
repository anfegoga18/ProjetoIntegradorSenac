package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Service.MercadoService;

/**
 *
 * @author Andrés Felipe González García
 */
@Controller
@RequestMapping("/mercados")
public class MercadoController {

    /*
        <a th:href="@{/mercados/cadastroMercados}">Cadastro de Mercado</a>
    */
    
    @Autowired
    private MercadoService mercadoService;
    
    /* Esta parte apresenta a tela para fazer o cadastro de um mercado*/
    @GetMapping("/cadastroMercados")
    public String formularioProduto(Model model){
        
        MercadoEntity mercado = new MercadoEntity();//Cria uma instância do mercado para adicionar ao modelo do formulário
        model.addAttribute("Mercado", mercado);//MUITO IMPORTANTE, essa chave "Mercado" é a que deve coincidir com quem é chamado no HTML
        
        return "cadastroMercado";
    }
    
}
