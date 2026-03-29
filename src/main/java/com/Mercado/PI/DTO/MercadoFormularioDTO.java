package com.Mercado.PI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MercadoFormularioDTO {

    /*Atributos do mercado */
    private int MercadoId;
    private String bairro;
    private String endereco;
    private String numero;
    private String cep;
    private String cidade;
    private String telefone;
    private String site;
    private String teleEntrega;
    private String uf;

    /*Atributos da Red do Mercado */
    private int redId;
    private String nomeRed;
    
}
