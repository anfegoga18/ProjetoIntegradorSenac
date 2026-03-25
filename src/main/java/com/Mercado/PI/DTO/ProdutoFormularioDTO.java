package com.Mercado.PI.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoFormularioDTO {

    /*Atributos do produto */
    private int PordutoId;
    private String nome;
    private String tipo;
    private String categoria;
    private String marca;
    private String udMedida;
    private double quantidadeConteudo;
    private boolean status;

    /*Atributos do Mercado */
    private int MercadoId;
    private String bairro;

    /*Atributos do ProdutoMercado (Tabela Intermediária) */
    private Double preco;
    private String moeda;
    private LocalDate dataPreco;
    private LocalDate dataValidade;

}
