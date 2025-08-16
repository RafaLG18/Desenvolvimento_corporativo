/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exemplo.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
@Entity
@Table(name="TB_FORNECEDOR") 
@DiscriminatorValue(value = "Fornecedor")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Fornecedor extends Usuario{
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "TB_USUARIO_SERVICO", joinColumns = {
        @JoinColumn(name = "ID_USUARIO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_SERVICO")})
    private List<Servico> servicos;
    
    public void addServico(Servico servico){
        if(servicos==null){
            servicos=new ArrayList<>();
        }
        this.servicos.add(servico);
    }
    
    public List<Servico> getServicos(){
        return servicos;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("exemplo.jpa.Fornecedor[");
        sb.append(this.servicos);
        sb.append(", ");
        sb.append("]");
        return sb.toString();
    }
    
}
