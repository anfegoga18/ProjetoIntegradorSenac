package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Data.RedEntity;
import com.Mercado.PI.Service.MercadoService;
import com.Mercado.PI.Service.RedService;

/**
 *
 * @author Andrés Felipe González García
 */
@Controller
@RequestMapping("/mercados")
public class MercadoController {

    /*
        <a th:href="@{/mercados/cadastroMercados}">Cadastro de Mercado</a>
        <a th:href="@{/mercados/cadastroMercados/red}">Cadastro Red de Mercados</a>
    */
    
    @Autowired
    private MercadoService mercadoService;

    @Autowired
    private RedService redService;

    
    /* Esta parte apresenta a tela para fazer o cadastro de um mercado*/
    @GetMapping("/cadastroMercados")
    public String formularioMercado(Model model){
        
        MercadoEntity mercado = new MercadoEntity();//Cria uma instância do mercado para adicionar ao modelo do formulário
        
        mercado.setRedMercado(new RedEntity()); //Inicializa com uma RedEntity nova para não quebrar no formulário por chegar como null

        model.addAttribute("Mercado", mercado);//MUITO IMPORTANTE, essa chave "Mercado" é a que deve coincidir com quem é chamado no HTML
        model.addAttribute("Redes", redService.listarRedesMercados()); //Cá está procurando todas as redes de mercado cadastradas e as chamando de "Redes"

        return "cadastroMercado";
    }
    
    /* Esta parte envia os dados inseridos pelo usuário para o cadastro do mercado */
    @PostMapping("/cadastroMercados")
    public String processarFormMercado(@ModelAttribute MercadoEntity mercado, Model model){//O @ModelAttribute significa que o que vai ser passado no Model é um objeto MercadoEntity chamado mercado
        
        //Verificando se a red de mercados existe antes de adicioná-la no banco de dados
        int redId = mercado.getRedMercado().getId();
        //RedEntity red = redService.buscarRedPorId(redId).orElseThrow(() -> new RuntimeException("Red não existe"));
        RedEntity red = redService.buscarRedPorId(redId).orElse(null);
    

        if(red != null){
            mercado.setRedMercado(red);
            mercadoService.salvar(mercado); //Persistindo o mercado no BD
        } else {
            System.out.println("Não foi encontrada a red de mercados");
            return "cadastroMercado";
        }

    return"cadastroMercadoSucesso";
    }
    

    /********************  CONTROLLER DA RED DE MERCADOS **********************/


    /* Esta parte apresenta a tela para fazer o cadastro da red de mercados*/
    @GetMapping("/cadastroMercados/red")
    public String formularioRed(Model model){
        
        RedEntity red = new RedEntity();//Cria uma instância da red de mercados para adicionar ao modelo do formulário
        model.addAttribute("Red", red);//MUITO IMPORTANTE, essa chave "Red" é a que deve coincidir com quem é chamado no HTML
        
        return "cadastroRed";
    }
    
    /* Esta parte envia os dados inseridos pelo usuário para o cadastro da red de mercados */
    @PostMapping("/cadastroMercados/red")
    public String processarFormRed(@ModelAttribute RedEntity red, Model model){//O @ModelAttribute significa que o que vai ser passado no Model é um objeto RedEntity chamado red
        
        redService.salvar(red); //Persistindo a red do mercado no BD
        model.addAttribute("Red", red);//Adicionando a red do mercado ao modelo para posterior exibição na vista
        model.addAttribute("Mercado", new MercadoEntity()); //Cria uma nova entidade de mercado para a exibição do formulário de cadastro de mercado
        
    return"redirect:/cadastroMercado";
    }
}
