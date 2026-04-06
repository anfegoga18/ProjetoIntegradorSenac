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
import lombok.ToString;

@Entity
//@Data //Para não precisar escrever os getters e setters
@Getter
@Setter
@ToString(exclude = {"mercados"}) //Devi adicionar porque estava entrando em loop com MercadoEntity
@NoArgsConstructor //Para construtor sem argumentos
@AllArgsConstructor //Para construtor com todos os argumentos
@Table(name = "red")
public class RedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeRed;

    @OneToMany(mappedBy = "redMercado", cascade = CascadeType.ALL)//Com esse nome a entidade Red aparece declarada na  entidade mercado
    @JsonManagedReference //Entidade pai de mercados
    private List<MercadoEntity> mercados;

}
