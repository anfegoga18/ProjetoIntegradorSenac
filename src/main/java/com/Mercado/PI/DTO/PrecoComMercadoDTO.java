package com.Mercado.PI.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrecoComMercadoDTO {

    /*Atributos do Mercado */
    private String bairro;
    private String redMercado;

    /*Atributos do ProdutoMercado (Tabela Intermediária) */
    private Double preco;
    private String moeda;
    private LocalDate dataPreco;
    private LocalDate dataValidade;

}
