package com.Mercado.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mercado.PI.DTO.MercadoFormularioDTO;
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
        
        MercadoFormularioDTO mercadoForm = new MercadoFormularioDTO();//Cria uma instância do MercadoFormularioDTO para adicionar ao modelo do formulário
        
        //mercado.setRedMercado(new RedEntity()); //Inicializa com uma RedEntity nova para não quebrar no formulário por chegar como null

        model.addAttribute("MercadoForm", mercadoForm);//MUITO IMPORTANTE, essa chave "MercadoForm" é a que deve coincidir com quem é chamado no HTML
        model.addAttribute("Redes", redService.listarRedesMercados()); //Cá está procurando todas as redes de mercado cadastradas e as chamando de "Redes"

        return "cadastroMercado";
    }
    
    /* Esta parte envia os dados inseridos pelo usuário para o cadastro do mercado */
    @PostMapping("/cadastroMercados")
    public String processarFormMercado(@ModelAttribute MercadoFormularioDTO mercadoForm, Model model){//O @ModelAttribute significa que o que vai ser passado no Model é um objeto MercadoFormularioDTO chamado mercadoForm

        //Salvando o mercado no BD
        MercadoEntity mercadoSalvo = mercadoService.salvar(mercadoForm);
        
        //Como estou trabalhando com DTO e até salvar não é gerado id no mercado, devo asignar o id no DTO
        mercadoForm.setMercadoId(mercadoSalvo.getId());

        //É necessário também passar o nome da red de supermercado, pois estou só trabalhando com o id dela
        mercadoForm.setNomeRed(mercadoSalvo.getRedMercado().getNomeRed());
        
        //Passando o objeto no model para a view
        model.addAttribute("mercadoForm", mercadoForm);

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
        
    return"redirect:/mercados/cadastroMercados";
    }
}
