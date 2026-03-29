package com.Mercado.PI.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "mercado")
public class MercadoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bairro;
    private String endereco;
    private String numero;
    private String cep;
    private String cidade;
    private String telefone;
    private String site;
    private String teleEntrega;
    private String uf;

    @ManyToOne //Muitos mercados em diferentes endereços pertencem a uma red de mercados
    @JoinColumn(name = "redMercado_id", nullable = false) //Não pode existir um mercado sem estar associado a uma rede, nem que seja um único mercado, por isso o nullable false
    @JsonBackReference //Isto para evitar o erro de loop em Json, essa pertence a entidade filha
    private RedEntity redMercado;

    @OneToMany(mappedBy = "mercado", cascade = CascadeType.ALL) //Está declarada como "mercado" na entidade ProdutoMercadoEntity
    @JsonManagedReference
    private List<ProdutoMercadoEntity> produtosMercados;

}
