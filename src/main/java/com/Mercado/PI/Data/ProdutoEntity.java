package com.Mercado.PI.Data;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Andrés Felipe González García
 */


@Entity
@Data //Para não precisar escrever os getters e setters
@NoArgsConstructor //Para construtor sem argumentos
@AllArgsConstructor //Para construtor com todos os argumentos
@Table(name = "produto")
public class ProdutoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String tipo;
    private String categoria;
    private String marca;
    private String ud_medida;
    private double quantidade_conteudo;
    private double preco;
    private String moeda;
    private LocalDate data_preco;
    private LocalDate data_validade;
    private boolean status;
    
    //Construtor criado para poder pegá-lo e fazer a busca de produto (sem mais informações sobre ele)
    public ProdutoEntity (String nome){
        this.nome = nome;
    }
    
}
