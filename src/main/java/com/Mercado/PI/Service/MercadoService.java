package com.Mercado.PI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mercado.PI.DTO.MercadoFormularioDTO;
import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Data.RedEntity;
import com.Mercado.PI.Repository.MercadoRepository;
import com.Mercado.PI.Repository.RedRepository;


/**
 *
 * @author Andrés Felipe González García
 */

@Service
public class MercadoService {

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private RedRepository redRepository;
    
    
    public List<MercadoEntity> listarMercados(){
        return mercadoRepository.findAll();
    }
    
    public Optional<MercadoEntity> buscarMercadoPorId(Integer id){ //O Optional é caso não ache o Mercado já faz o gerenciamento
        return mercadoRepository.findById(id);
    }
    
    public MercadoEntity salvar(MercadoFormularioDTO mercadoForm){

        //Para o mercado. Primeiro vou conferir se já existe o mercado usando o bairro, cidade e cep antes de cadastrar um novo
        MercadoEntity mercado = mercadoRepository.findByBairroIgnoreCaseAndCepIgnoreCaseAndCidadeIgnoreCase(
                                                                    mercadoForm.getBairro(), 
                                                                    mercadoForm.getCep(),
                                                                    mercadoForm.getCidade()
                                                                ).orElseGet(
                                                                    () -> {
                                                                    
                                                                    MercadoEntity mercadoNovo = new MercadoEntity();

                                                                    mercadoNovo.setBairro(mercadoForm.getBairro());
                                                                    mercadoNovo.setEndereco(mercadoForm.getEndereco());
                                                                    mercadoNovo.setNumero(mercadoForm.getNumero());
                                                                    mercadoNovo.setCep(mercadoForm.getCep());
                                                                    mercadoNovo.setCidade(mercadoForm.getCidade());
                                                                    mercadoNovo.setTelefone(mercadoForm.getTelefone());
                                                                    mercadoNovo.setSite(mercadoForm.getSite());
                                                                    mercadoNovo.setTeleEntrega(mercadoForm.getTeleEntrega());
                                                                    mercadoNovo.setUf(mercadoForm.getUf());

                                                                    return mercadoNovo;

                                                                    });

        //System.out.println("El mercado nuevo es: "  + mercado);
        /*                                                             
        if(!mercado.isEmpty()){
            throw new RuntimeException("Existe já um mercado nesse bairro com o mesmo cep e na mesma cidade");
        } else {
            return mercadoRepository.save(mercado);
        } */

        //Para a red do mercado
        Integer red = mercadoForm.getRedId(); //Obtendo o id da red do mercado no mercadoForm
        //System.out.println("La rede del mercado es: "+ red);
        RedEntity redMercado = redRepository.findById(red).orElseThrow(); // Procurando pela red do mercado no BD
        //System.out.println("La redMercadoEntity es: "+ redMercado);

        mercado.setRedMercado(redMercado); // Salvando a red do mercado no objeto mercado inicial
        //System.out.println(mercado);

        return mercadoRepository.save(mercado);
    }

    public void excluir(Integer id){
        mercadoRepository.deleteById(id);
    }
}
