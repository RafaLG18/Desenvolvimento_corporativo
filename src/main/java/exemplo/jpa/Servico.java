/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
@Entity
@Table(name = "TB_SERVICO")
public class Servico {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "TXT_NOME", nullable = false, length = 50, unique = false)
    private String nome;
    @NotBlank
    @Column(name = "TXT_TIPO", nullable = false, length = 50, unique = false)
    private String tipo;
    @NotNull
    @Column(name = "TXT_PRECO", nullable = false, length = 50, unique = false)
    private double preco;
    @ManyToMany(mappedBy="servicos")
    private List<Fornecedor> fornecedores = new ArrayList<>();
    
    // Fornecedores
    public List<Fornecedor> getFornecedores(){
        return this.fornecedores;
    }
    public void addFornecedor(Fornecedor fornecedor){
        if(fornecedores ==null){
            fornecedores=new ArrayList<>();
        }
        this.fornecedores.add(fornecedor);
    }
    // Id
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    
    // Nome
    public void setNome(String nome){
        this.nome=nome;
    }
    public String getNome(){
        return this.nome;
    }
    
    // Tipo
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    public String getTipo(){
        return this.tipo;
    }
    
    // Preco
    public void setPreco(double preco){
        this.preco=preco;
    }
    public double getPreco(){
        return this.preco;
    }
}
