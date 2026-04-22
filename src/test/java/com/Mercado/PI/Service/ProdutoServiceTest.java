package com.Mercado.PI.Service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Mercado.PI.DTO.ProdutoFormularioDTO;
import com.Mercado.PI.Data.MercadoEntity;
import com.Mercado.PI.Data.ProdutoEntity;
import com.Mercado.PI.Data.ProdutoMercadoEntity;
import com.Mercado.PI.Data.RedEntity;
import com.Mercado.PI.Repository.MercadoRepository;
import com.Mercado.PI.Repository.ProdutoMercadoRepository;
import com.Mercado.PI.Repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class) // Para que as anotações do Mockito surtam efeito
public class ProdutoServiceTest {

    @Mock // Serve para criar os objetos falsos que farão o teste
    private ProdutoRepository produtoRepository;

    @Mock
    private MercadoRepository mercadoRepository;

    @Mock
    private ProdutoMercadoRepository produtoMercadoRepository;

    @InjectMocks // Para pegar o produtoRepository e inseri-lo dentro do produtoService (Atenção que são os objetos e não as entidades, começam com letra minúscula)
    private ProdutoService produtoService;


    //Lembrar que os testes devem cumprir o fator AAA - Arrange/Act/Assert
    @Test
    public void testSalvarProdutoNovo(){

    //ARRANGE (Prepara o terreno para o test)

        // Criamos os ProdutoFormularioDTO simulando o que viria da view (Datos do DTO)
        ProdutoFormularioDTO formularioDeTeste = new ProdutoFormularioDTO();
        //Escolhi 3 atributos do produto que gostaria de verificar integridade ao final do teste e ignorei o restante, vou definir a resposta como any() que significa que não me importo com o que vier específicamente nesses campos
        formularioDeTeste.setNome("Leite");
        formularioDeTeste.setMarca("Pia");
        formularioDeTeste.setTipo("Integral");

        //Para o mercado devo definir o id pois meu serviçe o utiliza para fazer a busca do mercado
        formularioDeTeste.setMercadoId(10);
    
        //Para a tabela intermediaria ProdutoMercado só vou'ver integridade de 2 atributos
        formularioDeTeste.setPreco(4.85);
        formularioDeTeste.setDataPreco(LocalDate.now());
    
        //Mock do produto (ProdutoEntity), como queremos que crie um produto novo, devemos retornar vazio e o any() desconsidera o que vir em cada campo, neste momento o impotante é criar o produto novo indo para o orElseGet()
        when(produtoRepository.findByNomeIgnoreCaseAndTipoIgnoreCaseAndCategoriaIgnoreCaseAndMarcaIgnoreCaseAndUdMedidaIgnoreCaseAndQuantidadeConteudoAndStatus(
                  any(), any(), any(), any(), any(), anyDouble(), anyBoolean())).thenReturn(Optional.empty());

    
        //Mock do mercado
        MercadoEntity mercadoTeste = new MercadoEntity();
        mercadoTeste.setId(10);
        mercadoTeste.setBairro("Cidade Baixa");

        RedEntity redMercadoTeste = new RedEntity();//Deve ser criada uma red de mercado do zero para associá-la ao mercado criado também
        redMercadoTeste.setNomeRed("Zaffari");
       
        mercadoTeste.setRedMercado(redMercadoTeste);

        when(mercadoRepository.findById(10)).thenReturn(Optional.of(mercadoTeste));

        //Mock da relação produtoMercado simulando que está sendo inserida uma nova relação
        when(produtoMercadoRepository.findByProdutoAndMercadoAndDataPreco(any(), any(), any())).thenReturn(Optional.empty());

    //ACT (Executando o servico para salvar um novo produto)

        ProdutoFormularioDTO produtoNovo = produtoService.salvar(formularioDeTeste);

    //ASSERT (Vamos verificar o que foi salvo e se realmente foi)

        //Verificando se foi executado pelo menos 1 vez o salvado de um objeto ProdutoEntity, no caso a criação de um novo produto
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class)); 
        
        //Verificando se foi executado pelo menos 1 vez o salvado de um objeto ProdutoMercadoEntity, no caso a criação de uma nova relação mercadoProduto
        verify(produtoMercadoRepository, times(1)).save(any(ProdutoMercadoEntity.class));

        //Vamos verificar se os dados passados nos Mocks foram realmente os enviados
        assertEquals("Leite", produtoNovo.getNome());
        assertEquals("Pia", produtoNovo.getMarca());
        assertEquals("Integral", produtoNovo.getTipo());

        assertEquals("Cidade Baixa", produtoNovo.getBairro());
        assertEquals("Zaffari", produtoNovo.getRedMercado());

        assertEquals(4.85, produtoNovo.getPreco());
        assertEquals(LocalDate.now(), produtoNovo.getDataPreco());

    }

}