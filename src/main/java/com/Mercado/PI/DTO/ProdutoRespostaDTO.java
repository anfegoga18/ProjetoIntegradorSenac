package com.Mercado.PI.DTO;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoRespostaDTO {

    private Integer produtoId;
    private String nomeProduto;
    private String tipo;
    private String categoria;
    private String marca;
    private String udMedida;
    private double quantidadeConteudo;
    private boolean status;

    private List<PrecoComMercadoDTO> precosMercados; 

}
