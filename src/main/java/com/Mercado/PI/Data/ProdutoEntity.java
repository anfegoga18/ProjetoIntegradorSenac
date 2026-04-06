package com.Mercado.PI.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Andrés Felipe González García
 */


@Entity
//@Data //Para não precisar escrever os getters e setters
@Getter
@Setter
@NoArgsConstructor //Para construtor sem argumentos
@AllArgsConstructor //Para construtor com todos os argumentos
@Table(name = "produto")
public class ProdutoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;
    private String categoria;
    private String marca;
    private String udMedida;
    private double quantidadeConteudo;
    //private double preco;
    //private String moeda;
    //private LocalDate dataPreco;
    //private LocalDate dataValidade;
    private boolean status;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL) //Está declarada como "produto" na entidade ProdutoMercadoEntity
    @JsonManagedReference
    private List<ProdutoMercadoEntity> produtosMercados;
    

    //Construtor criado para poder pegá-lo e fazer a busca de produto (sem mais informações sobre ele)
    public ProdutoEntity (String nome){
        this.nome = nome;
    }
    
}
