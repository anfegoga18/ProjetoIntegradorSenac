package com.Mercado.PI.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Para não precisar escrever os getters e setters
@NoArgsConstructor //Para construtor sem argumentos
@AllArgsConstructor //Para construtor com todos os argumentos
@Table(name = "produto_mercado")
public class ProdutoMercadoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name ="produto_id")
    @JsonBackReference
    private ProdutoEntity produto;
    
    @ManyToOne
    @JoinColumn(name = "mercado_id")
    @JsonBackReference
    private MercadoEntity mercado;
    
    private Double preco;
    private String moeda;
    private LocalDate dataPreco;
    private LocalDate dataValidade;

}
